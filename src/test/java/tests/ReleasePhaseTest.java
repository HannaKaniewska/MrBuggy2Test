package tests;

import businessLayer.PhaseBL;
import businessLayer.ReleaseBL;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.JsonUtils;
import utils.ProjectStatus;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReleasePhaseTest extends BaseTest {

    private final Logger log = Logger.getLogger(EnvironmentTest.class);

    @Test(dataProvider = "getData")
    public void addDeleteReleasePhase(ReleaseBL testData) throws IOException {
        testReport = reports.createTest("Add and delete release and phase");
        SoftAssert softAssert = new SoftAssert();
        String projectName;

        //Step 1. Log in
        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
        testReport.pass("1. Log in", getScreenShot());

        //Prepare project for testing:
        //check if project exists and is active, if not -> skip test
        ProjectListPage projectListPage = cockpitPage.enterAdminPanel()
                .performSearch(testData.projectPrefix);
        ProjectStatus projectStatus = projectListPage.getProjectStatus();
        if (projectStatus != ProjectStatus.ACTIVE) {
            testReport.skip("Project is not active", getScreenShot());
            throw new SkipException("Project is not active");
        }
        else {
            projectName = projectListPage.getFirstProjectName();
        }
        cockpitPage = projectListPage.leaveAdminPanel();

        //Step 2. Select project on the top panel
        cockpitPage.setActiveProject(projectName);
        Assert.assertTrue(cockpitPage.isProjectSet(projectName));
        testReport.pass("2. Select project on the top panel", getScreenShot());

        //Step 3. Select Release menu item
        cockpitPage.setActiveMenuElement("Wydania");
        ReleaseListPage releaseListPage = new ReleaseListPage(driver);
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());
        testReport.pass("3. Select Release menu item", getScreenShot());

        //Check if release already exists, if not -> add new release
        releaseListPage.performSearch(testData.releaseName);
        if (!releaseListPage.isReleaseFound(testData.releaseName)) {

            //Step 4. Press Add release button
            AddReleasePage addReleasePage = releaseListPage.pressAddReleaseButton();
            Assert.assertTrue(addReleasePage.isCorrectPageLoaded());
            testReport.pass("4. Press Add release button", getScreenShot());

            //Step 5, 6. Add new release
            releaseListPage = addReleasePage.performAddRelease(testData.releaseName,
                    testData.releaseStartDate, testData.releaseEndDate, testData.releaseDescription);
            Assert.assertTrue(releaseListPage.isCorrectPageLoaded());
            softAssert.assertTrue(releaseListPage.getMessageTest()
                    .equals("Wydanie zostało dodane."));
            testReport.pass("5, 6. Add new release", getScreenShot());
        }

        //Step 7. Search for new release on the list
        releaseListPage.performSearch(testData.releaseName);
        Assert.assertTrue(releaseListPage.isReleaseFound(testData.releaseName));
        testReport.pass("7. Search for new release on the list", getScreenShot());

        //Step 8. Select Phases menu item
        cockpitPage.setActiveMenuElement("Fazy");
        PhaseListPage phaseListPage = new PhaseListPage(driver);
        Assert.assertTrue(phaseListPage.isCorrectPageLoaded());
        testReport.pass("8. Select Phases menu item", getScreenShot());

        //For each phase in Test Data: add phase, search for it on the list, delete phase
        for (PhaseBL phaseData : testData.phases) {

            //check if phase already exists, if not -> Add new phase
            phaseListPage.performSearch(phaseData.phaseName);
            if (!phaseListPage.isPhaseFound(phaseData.phaseName)) {

                //Step 9. Press Add phase button
                AddPhasePage addPhasePage = phaseListPage.pressAddPhaseButton();
                Assert.assertTrue(addPhasePage.isCorrectPageLoaded());
                testReport.pass("9. Press Add phase button", getScreenShot());

                //Step 10, 11. Add new phase
                phaseListPage = addPhasePage.performAddPhase(phaseData.phaseName, testData.releaseName,
                        phaseData.phaseStartDate, phaseData.phaseEndDate, phaseData.phaseDescription);
                Assert.assertTrue(phaseListPage.isCorrectPageLoaded());
                softAssert.assertTrue(phaseListPage.getMessageTest()
                        .equals("Faza została dodana."));
                testReport.pass("10, 11. Add new phase", getScreenShot());

            }

            //Step 12. Search for new phase on the list
            phaseListPage.performSearch(phaseData.phaseName);
            Assert.assertTrue(phaseListPage.isPhaseFound(phaseData.phaseName));
            testReport.pass("12. Search for new phase on the list", getScreenShot());

            //Step 13. Show details of the new phase
            PhaseDetailsPage phaseDetailsPage = phaseListPage.showPhaseDetails(phaseData.phaseName);
            Assert.assertTrue(phaseDetailsPage.isCorrectPageLoaded());
            testReport.pass("13. Show details of the new phase", getScreenShot());

            //Step 14, 15. Delete phase
            phaseListPage = phaseDetailsPage.performDeletePhase();
            softAssert.assertTrue(phaseListPage.getMessageTest()
                    .equals("Faza została usunięta."));
            Assert.assertTrue(phaseListPage.isCorrectPageLoaded());
            testReport.pass("14, 15. Delete phase", getScreenShot());
        }

        //Step 16. Select Release menu item
        cockpitPage.setActiveMenuElement("Wydania");
        releaseListPage = new ReleaseListPage(driver);
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());
        testReport.pass("16. Select Release menu item", getScreenShot());

        //Step 17. Show details of the release
        releaseListPage.performSearch(testData.releaseName);
        ReleaseDetailsPage releaseDetailsPage = releaseListPage.showReleaseDetails(testData.releaseName);
        Assert.assertTrue(releaseDetailsPage.isCorrectPageLoaded());
        testReport.pass("17. Show details of the release", getScreenShot());

        //Step 18, 19. Delete release
        releaseListPage = releaseDetailsPage.performDeleteRelease();
        softAssert.assertTrue(releaseListPage.getMessageTest()
                .equals("Wydanie zostało usunięte."));
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());
        testReport.pass("18, 19. Delete release", getScreenShot());
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getReleasePhaseData();
    }

}

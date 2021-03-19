package tests;

import businessLayer.PhaseBL;
import businessLayer.ReleaseBL;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.ExtentTestNGTestListener;
import utils.JsonUtils;
import utils.ProjectStatus;

import java.io.FileNotFoundException;
import java.io.IOException;

@Listeners(ExtentTestNGTestListener.class)
public class ReleasePhaseTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void addDeleteReleasePhase(ReleaseBL testData) {
        SoftAssert softAssert = new SoftAssert();
        String projectName;

        //Step 1. Log in
        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());

        //Prepare project for testing:
        //check if project exists and is active, if not -> skip test
        ProjectListPage projectListPage = cockpitPage.enterAdminPanel()
                .performSearch(testData.projectPrefix);
        ProjectStatus projectStatus = projectListPage.getProjectStatus();
        if (projectStatus != ProjectStatus.ACTIVE) {
            throw new SkipException("Project is not active");
        }
        else {
            projectName = projectListPage.getFirstProjectName();
        }
        cockpitPage = projectListPage.leaveAdminPanel();

        //Step 2. Select project on the top panel
        cockpitPage.setActiveProject(projectName);
        Assert.assertTrue(cockpitPage.isProjectSet(projectName));

        //Step 3. Select Release menu item
        cockpitPage.setActiveMenuElement("Wydania");
        ReleaseListPage releaseListPage = new ReleaseListPage(driver);
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());

        //Check if release already exists, if not -> add new release
        releaseListPage.performSearch(testData.releaseName);
        if (!releaseListPage.isReleaseFound(testData.releaseName)) {

            //Step 4. Press Add release button
            AddReleasePage addReleasePage = releaseListPage.pressAddReleaseButton();
            Assert.assertTrue(addReleasePage.isCorrectPageLoaded());

            //Step 5, 6. Add new release
            releaseListPage = addReleasePage.performAddRelease(testData.releaseName,
                    testData.releaseStartDate, testData.releaseEndDate, testData.releaseDescription);
            Assert.assertTrue(releaseListPage.isCorrectPageLoaded());
            softAssert.assertTrue(releaseListPage.getMessageTest()
                    .equals("Wydanie zostało dodane."));
        }

        //Step 7. Search for new release on the list
        releaseListPage.performSearch(testData.releaseName);
        Assert.assertTrue(releaseListPage.isReleaseFound(testData.releaseName));

        //Step 8. Select Phases menu item
        cockpitPage.setActiveMenuElement("Fazy");
        PhaseListPage phaseListPage = new PhaseListPage(driver);
        Assert.assertTrue(phaseListPage.isCorrectPageLoaded());

        //For each phase in Test Data: add phase, search for it on the list, delete phase
        for (PhaseBL phaseData : testData.phases) {

            //check if phase already exists, if not -> Add new phase
            phaseListPage.performSearch(phaseData.phaseName);
            if (!phaseListPage.isPhaseFound(phaseData.phaseName)) {

                //Step 9. Press Add phase button
                AddPhasePage addPhasePage = phaseListPage.pressAddPhaseButton();
                Assert.assertTrue(addPhasePage.isCorrectPageLoaded());

                //Step 10, 11. Add new phase
                phaseListPage = addPhasePage.performAddPhase(phaseData.phaseName, testData.releaseName,
                        phaseData.phaseStartDate, phaseData.phaseEndDate, phaseData.phaseDescription);
                Assert.assertTrue(phaseListPage.isCorrectPageLoaded());
                softAssert.assertTrue(phaseListPage.getMessageTest()
                        .equals("Faza została dodana."));

            }

            //Step 12. Search for new phase on the list
            phaseListPage.performSearch(phaseData.phaseName);
            Assert.assertTrue(phaseListPage.isPhaseFound(phaseData.phaseName));

            //Step 13. Show details of the new phase
            PhaseDetailsPage phaseDetailsPage = phaseListPage.showPhaseDetails(phaseData.phaseName);
            Assert.assertTrue(phaseDetailsPage.isCorrectPageLoaded());

            //Step 14, 15. Delete phase
            phaseListPage = phaseDetailsPage.performDeletePhase();
            softAssert.assertTrue(phaseListPage.getMessageTest()
                    .equals("Faza została usunięta."));
            Assert.assertTrue(phaseListPage.isCorrectPageLoaded());
        }

        //Step 16. Select Release menu item
        cockpitPage.setActiveMenuElement("Wydania");
        releaseListPage = new ReleaseListPage(driver);
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());

        //Step 17. Show details of the release
        releaseListPage.performSearch(testData.releaseName);
        ReleaseDetailsPage releaseDetailsPage = releaseListPage.showReleaseDetails(testData.releaseName);
        Assert.assertTrue(releaseDetailsPage.isCorrectPageLoaded());

        //Step 18, 19. Delete release
        releaseListPage = releaseDetailsPage.performDeleteRelease();
        softAssert.assertTrue(releaseListPage.getMessageTest()
                .equals("Wydanie zostało usunięte."));
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getReleasePhaseData();
    }

}

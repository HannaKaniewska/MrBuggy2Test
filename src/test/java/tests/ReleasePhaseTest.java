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

@Listeners(ExtentTestNGTestListener.class)
public class ReleasePhaseTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void addDeleteReleasePhase(ReleaseBL testData) {
        SoftAssert softAssert = new SoftAssert();
        String projectName;

        //Step 1. Log in
        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(loginEmail, loginPassword);
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());

        //Step 2. Enter Administration panel
        ProjectListPage projectListPage = cockpitPage.enterAdminPanel();
        Assert.assertTrue(projectListPage.isCorrectPageLoaded());
        softAssert.assertTrue(projectListPage.isMenuItemActive("Projekty"));

        //Step 3. Search for the project using prefix
        projectListPage.performSearch(testData.projectPrefix);
        //check if project exists and is active, if not -> skip test
        if (! projectListPage.isProjectFound()) {
            throw new SkipException("Project is not found");
        }
        ProjectStatus projectStatus = projectListPage.getProjectStatus();
        if (projectStatus != ProjectStatus.ACTIVE) {
            throw new SkipException("Project is not active");
        }
        //Get project name to use it for choosing active project on the top panel
        projectName = projectListPage.getFirstProjectName();

        //Step 4. Enter Details page for the project
        ProjectDetailsPage projectDetailsPage = projectListPage
                .showProjectDetails(projectName);
        Assert.assertTrue(projectDetailsPage.isCorrectPageLoaded());

        //Step 5. Add leader role to the project
        projectDetailsPage.addUserToProjectRole(leaderRoleName, loginEmail);
        softAssert.assertTrue(projectDetailsPage.isUserInProjectRole(leaderRoleName, loginEmail));

        //Step 6. Leave Administration panel
        cockpitPage = projectListPage.leaveAdminPanel();
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());

        //Step 7. Select project on the top panel
        cockpitPage.setActiveProject(projectName);
        Assert.assertTrue(cockpitPage.isProjectSet(projectName));

        //Step 8. Select Release menu item
        cockpitPage.setActiveMenuElement("Wydania");
        ReleaseListPage releaseListPage = new ReleaseListPage(driver);
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());

        //Check if release already exists, if not -> add new release
        releaseListPage.performSearch(testData.releaseName);
        if (!releaseListPage.isReleaseFound(testData.releaseName)) {

            //Step 9. Press Add release button
            AddReleasePage addReleasePage = releaseListPage.pressAddReleaseButton();
            Assert.assertTrue(addReleasePage.isCorrectPageLoaded());

            //Step 10. Add new release
            releaseListPage = addReleasePage.performAddRelease(testData.releaseName,
                    testData.releaseStartDate, testData.releaseEndDate, testData.releaseDescription);
            Assert.assertTrue(releaseListPage.isCorrectPageLoaded());
            softAssert.assertTrue(releaseListPage.getMessageTest()
                    .equals("Wydanie zostało dodane."));
        }

        //Step 11. Search for new release on the list
        releaseListPage.performSearch(testData.releaseName);
        Assert.assertTrue(releaseListPage.isReleaseFound(testData.releaseName));

        //Step 12. Select Phases menu item
        cockpitPage.setActiveMenuElement("Fazy");
        PhaseListPage phaseListPage = new PhaseListPage(driver);
        Assert.assertTrue(phaseListPage.isCorrectPageLoaded());

        //For each phase in Test Data: add phase, search for it on the list, delete phase
        for (PhaseBL phaseData : testData.phases) {


            //Step 13. Press Add phase button
            AddPhasePage addPhasePage = phaseListPage.pressAddPhaseButton();
            Assert.assertTrue(addPhasePage.isCorrectPageLoaded());

            //Step 14. Add new phase
            phaseListPage = addPhasePage.performAddPhase(phaseData.phaseName, testData.releaseName,
                    phaseData.phaseStartDate, phaseData.phaseEndDate, phaseData.phaseDescription);
            Assert.assertTrue(phaseListPage.isCorrectPageLoaded());
            softAssert.assertTrue(phaseListPage.getMessageTest()
                    .equals("Faza została dodana."));

            //Step 15. Search for new phase on the list
            phaseListPage.performSearch(phaseData.phaseName);
            Assert.assertTrue(phaseListPage.isPhaseFound(phaseData.phaseName));

            //Step 16. Show details of the new phase
            PhaseDetailsPage phaseDetailsPage = phaseListPage.showPhaseDetails(phaseData.phaseName);
            Assert.assertTrue(phaseDetailsPage.isCorrectPageLoaded());

            //Step 17. Delete phase
            phaseListPage = phaseDetailsPage.performDeletePhase();
            softAssert.assertTrue(phaseListPage.getMessageTest()
                    .equals("Faza została usunięta."));
            Assert.assertTrue(phaseListPage.isCorrectPageLoaded());
        }

        //Step 18. Select Release menu item
        cockpitPage.setActiveMenuElement("Wydania");
        releaseListPage = new ReleaseListPage(driver);
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());

        //Step 19. Show details of the release
        releaseListPage.performSearch(testData.releaseName);
        ReleaseDetailsPage releaseDetailsPage = releaseListPage.showReleaseDetails(testData.releaseName);
        Assert.assertTrue(releaseDetailsPage.isCorrectPageLoaded());

        //Step 20. Delete release
        releaseListPage = releaseDetailsPage.performDeleteRelease();
        softAssert.assertTrue(releaseListPage.getMessageTest()
                .equals("Wydanie zostało usunięte."));
        Assert.assertTrue(releaseListPage.isCorrectPageLoaded());
        releaseListPage.performSearch(testData.releaseName);
        Assert.assertFalse(releaseListPage.isReleaseFound(testData.releaseName));
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getReleasePhaseData();
    }

}

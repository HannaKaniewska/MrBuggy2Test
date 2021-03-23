package tests;

import businessLayer.EnvironmentBL;
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
public class EnvironmentTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void addDeleteEnvironmentTest(EnvironmentBL testData) {
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
            throw new SkipException("Project is found but is not active");
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

        //Step 8. Select Environment menu item
        cockpitPage.setActiveMenuElement("Środowiska");
        EnvironmentListPage environmentListPage = new EnvironmentListPage(driver);
        Assert.assertTrue(environmentListPage.isCorrectPageLoaded());

        //Check if environment already exists, if not -> Add new environment
        environmentListPage.performSearch(testData.name);
        if (!environmentListPage.isEnvironmentFound(testData.name)) {

            //Step 9. Press Add environment button
            AddEnvironmentPage addEnvironmentPage = environmentListPage.pressAddEnvironmentButton();
            Assert.assertTrue(addEnvironmentPage.isCorrectPageLoaded());

            //Step 10. Add new environment
            environmentListPage = addEnvironmentPage.performAddEnvironment(testData.name, testData.description);
            Assert.assertTrue(environmentListPage.isCorrectPageLoaded());
            softAssert.assertTrue(environmentListPage.getMessageTest()
                    .equals("Środowisko zostało dodane."));
            environmentListPage.performSearch(testData.name);
            Assert.assertTrue(environmentListPage.isEnvironmentFound(testData.name));

        }

        //Step 11. Show details page for the new environment
        EnvironmentDetailsPage environmentDetailsPage = environmentListPage
                .showEnvironmentDetails(testData.name);
        Assert.assertTrue(environmentDetailsPage.isCorrectPageLoaded());

        //Step 12. Delete environment
        environmentListPage = environmentDetailsPage.performDeleteEnvironment();
        softAssert.assertTrue(environmentListPage.getMessageTest()
                .equals("Środowisko zostało usunięte."));
        Assert.assertTrue(environmentListPage.isCorrectPageLoaded());
        environmentListPage.performSearch(testData.name);
        Assert.assertFalse(environmentListPage.isEnvironmentFound(testData.name));
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getEnvironmentData();
    }

}

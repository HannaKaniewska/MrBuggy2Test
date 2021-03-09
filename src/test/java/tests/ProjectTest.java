package tests;

import businessLayer.ProjectBL;
import businessLayer.ProjectUserBL;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddProjectPage;
import pages.LoginPage;
import pages.ProjectListPage;
import pages.ProjectDetailsPage;
import utils.JsonUtils;
import utils.ProjectStatus;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProjectTest extends BaseTest{

    private final Logger log = Logger.getLogger(LoginTest.class);

    @Test(dataProvider = "getData")
    public void addProjectTest(ProjectBL projectData) throws IOException {
        testReport = reports.createTest("Add project");
        SoftAssert softAssert = new SoftAssert();

        //TODO: użyć TestListenera, żeby obsłużyć wszystkie statusy asercji
        //Step 1. Log in
        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
        testReport.pass("1. Log in", getScreenShot());

        //Step 2. Enter Administration panel
        ProjectListPage projectListPage = cockpitPage.enterAdminPanel();
        Assert.assertTrue(projectListPage.isCorrectPageLoaded());
        softAssert.assertTrue(projectListPage.isMenuItemActive("Projekty"));
        testReport.pass("2. Enter Administration panel", getScreenShot());

        //Step 3. Search for project using prefix
        projectListPage.performSearch(projectData.projectPrefix);
        testReport.pass("3. Search for project using prefix", getScreenShot());

        //Check if project with this prefix already exists
        if (projectListPage.isProjectFound(projectData.projectName)) {
            //Project already exists, check it's status. If the project is closed, skip the test.
            ProjectStatus status = projectListPage.getProjectStatus();
            if (status == ProjectStatus.CLOSED) {
                testReport.skip("Project is closed", getScreenShot());
                throw new SkipException("Project is closed");
            }
        }
        else {
            //Project doesn't exist, add new project
            //Step 4. Press 'Add project' button
            AddProjectPage addProjectPage = projectListPage.pressAddProjectButton();
            Assert.assertTrue(addProjectPage.isCorrectPageLoaded());
            testReport.pass("4. Press 'Add project' button", getScreenShot());

            //Step 5. Add new project
            projectListPage = addProjectPage
                    .performAddProject(projectData.projectName, projectData.projectPrefix);
            Assert.assertTrue(projectListPage.isProjectFound(projectData.projectName));
            testReport.pass("5. Add new project", getScreenShot());
        }

        //Step 6. Enter Details page for the new project
        ProjectDetailsPage projectDetailsPage = projectListPage
                .showProjectDetails(projectData.projectName);
        Assert.assertTrue(projectDetailsPage.isCorrectPageLoaded());
        testReport.pass("6. Enter Details page for the new project", getScreenShot());

        //Step 7. Add users to project
        for (ProjectUserBL user: projectData.projectUsers) {
            projectDetailsPage.addUserToProjectRole(user.userRole, user.userEmail);
            softAssert.assertTrue(projectDetailsPage.isUserInProjectRole(user.userRole, user.userEmail));
        }
        testReport.pass("7. Add user to project as a Leader", getScreenShot());

        //Step 8. Leave Administration panel
        cockpitPage = projectListPage.leaveAdminPanel();
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
        testReport.pass("8. Leave Administration panel", getScreenShot());

        //Step 9. Select project on the top panel
        cockpitPage.setActiveProject(projectData.projectName);
        Assert.assertTrue(cockpitPage.isProjectSet(projectData.projectName));
        testReport.pass("9. Select project on the top panel", getScreenShot());
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getProjectData();
    }

}

package tests;

import businessLayer.ProjectBL;
import businessLayer.ProjectUserBL;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddProjectPage;
import pages.LoginPage;
import pages.ProjectListPage;
import pages.ProjectDetailsPage;
import utils.ExtentTestNGTestListener;
import utils.JsonUtils;
import utils.ProjectStatus;

import java.io.FileNotFoundException;

@Listeners(ExtentTestNGTestListener.class)
public class ProjectTest extends BaseTest{

    @Test(dataProvider = "getData")
    public void addProjectTest(ProjectBL projectData) {
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
        projectListPage.performSearch(projectData.projectPrefix);

        //Check if project with this prefix already exists
        if (projectListPage.isProjectFound()) {
            //Project already exists, check it's status. If the project is closed, skip the test.
            ProjectStatus status = projectListPage.getProjectStatus();
            if (status == ProjectStatus.CLOSED) {
                throw new SkipException("Project is closed");
            }
            //Get project name to use it for choosing active project on the top panel
            projectName = projectListPage.getFirstProjectName();
        }
        else {
            //Project doesn't exist, add new project
            //Step 4. Press 'Add project' button
            AddProjectPage addProjectPage = projectListPage.pressAddProjectButton();
            Assert.assertTrue(addProjectPage.isCorrectPageLoaded());

            //Step 5. Add new project
            projectListPage = addProjectPage
                    .performAddProject(projectData.projectName, projectData.projectPrefix);
            Assert.assertTrue(projectListPage.isProjectFound(projectData.projectName));
            projectName = projectData.projectName;
        }

        if (projectName.isEmpty()) {
            Assert.fail("Project name is not set");
        }

        //Step 6. Enter Details page for the new project
        ProjectDetailsPage projectDetailsPage = projectListPage
                .showProjectDetails(projectName);
        Assert.assertTrue(projectDetailsPage.isCorrectPageLoaded());

        //Step 7. Add users to project
        for (ProjectUserBL user: projectData.projectUsers) {
            projectDetailsPage.addUserToProjectRole(user.userRole, user.userEmail);
            softAssert.assertTrue(projectDetailsPage.isUserInProjectRole(user.userRole, user.userEmail));
        }

        //Step 8. Leave Administration panel
        cockpitPage = projectListPage.leaveAdminPanel();
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());

        //Step 9. Select project on the top panel
        cockpitPage.setActiveProject(projectName);
        Assert.assertTrue(cockpitPage.isProjectSet(projectName));
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getProjectData();
    }

}

package tests;

import businessLayer.ExplorationTaskBL;
import businessLayer.OtherTaskBL;
import businessLayer.TaskForwardToExecuteBL;
import businessLayer.TestCaseTaskBL;
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
public class TaskTest extends BaseTest {

    @Test(dataProvider = "getExplorationTaskData")
    public void addDeleteExplorationTask(ExplorationTaskBL testData) {

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

        //Step 8. Select Task Repository menu item
        cockpitPage.setActiveMenuElement("Baza zadań");
        TaskRepositoryPage taskRepositoryPage = new TaskRepositoryPage(driver);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());

        //Step 9. Press Add exploration task button
        AddExplorationTaskPage addExplorationTaskPage = taskRepositoryPage.pressAddExplorationTaskButton();
        Assert.assertTrue(addExplorationTaskPage.isCorrectPageLoaded());

        //Step 10. Add new exploration task
        taskRepositoryPage = addExplorationTaskPage.performAddExplorationTask(testData.taskName,
                testData.taskDuration, testData.testCard);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie eksploracyjne zostało dodane."));

        //Step 11. Search for new task on the list
        taskRepositoryPage.performSearch(testData.taskName);
        Assert.assertTrue(taskRepositoryPage.isTaskFound(testData.taskName));

        //Step 12.  Delete task on the list
        taskRepositoryPage.performDeleteTask(testData.taskName);
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie w bazie zadań zostało usunięte."));
    }

    @Test(dataProvider = "getTestCaseTaskData")
    public void addDeleteTestCaseTask(TestCaseTaskBL testData) {

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

        //Step 8. Select Task Repository menu item
        cockpitPage.setActiveMenuElement("Baza zadań");
        TaskRepositoryPage taskRepositoryPage = new TaskRepositoryPage(driver);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());

        //Step 9. Press Add test case task button
        AddTestCaseTaskPage addTestCaseTaskPage = taskRepositoryPage.pressAddTestCaseTaskButton();
        Assert.assertTrue(addTestCaseTaskPage.isCorrectPageLoaded());

        //Step 10. Add new test case task
        taskRepositoryPage = addTestCaseTaskPage.performAddTestCaseTask(testData.taskName,
                testData.taskDescription, testData.taskPresuppositions, testData.taskResult);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Przypadek testowy został dodany."));

        //Step 11. Search for new task on the list
        taskRepositoryPage.performSearch(testData.taskName);
        Assert.assertTrue(taskRepositoryPage.isTaskFound(testData.taskName));

        //Step 12.  Delete task on the list
        taskRepositoryPage.performDeleteTask(testData.taskName);
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie w bazie zadań zostało usunięte."));

    }

    @Test(dataProvider = "getOtherTaskData")
    public void addDeleteOtherTask(OtherTaskBL testData) {
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

        //Step 8. Select Task Repository menu item
        cockpitPage.setActiveMenuElement("Baza zadań");
        TaskRepositoryPage taskRepositoryPage = new TaskRepositoryPage(driver);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());

        //Step 9. Press Add other task button
        AddOtherTaskPage addOtherTaskPage = taskRepositoryPage.pressAddOtherTaskButton();
        Assert.assertTrue(addOtherTaskPage.isCorrectPageLoaded());

        //Step 10. Add new other task
        taskRepositoryPage = addOtherTaskPage.performAddOtherTask(testData.taskName,
                testData.taskDescription);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie zostało dodane."));

        //Step 11. Search for new task on the list
        taskRepositoryPage.performSearch(testData.taskName);
        Assert.assertTrue(taskRepositoryPage.isTaskFound(testData.taskName));

        //Step 12.  Delete task on the list
        taskRepositoryPage.performDeleteTask(testData.taskName);
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie w bazie zadań zostało usunięte."));

    }

    @Test(dataProvider = "getExplorationTaskData")
    public void forwardToExecuteExplorationTask(ExplorationTaskBL testData) {
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

        //Step 8. Select Task Repository menu item
        cockpitPage.setActiveMenuElement("Baza zadań");
        TaskRepositoryPage taskRepositoryPage = new TaskRepositoryPage(driver);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());

        //Step 9. Press Add exploratory task button
        AddExplorationTaskPage addExplorationTaskPage = taskRepositoryPage.pressAddExplorationTaskButton();
        Assert.assertTrue(addExplorationTaskPage.isCorrectPageLoaded());

        //Step 10. Add new exploratory task
        taskRepositoryPage = addExplorationTaskPage.performAddExplorationTask(testData.taskName,
                testData.taskDuration, testData.testCard);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie eksploracyjne zostało dodane."));

        //For each task forward to execute data
        for (TaskForwardToExecuteBL forwardData : testData.taskForwardToExecuteData) {

            //Step 11. Search for new task on the list
            taskRepositoryPage.performSearch(testData.taskName);
            Assert.assertTrue(taskRepositoryPage.isTaskFound(testData.taskName));

            //Step 12. Choose Forward to Execute Action
            TaskForwardToExecutePage taskForwardToExecutePage = taskRepositoryPage
                    .performForwardToExecuteTask(testData.taskName);
            Assert.assertTrue(taskForwardToExecutePage.isCorrectPageLoaded());

            //Step 13. Perform forward to execute task
            taskRepositoryPage = taskForwardToExecutePage.performTaskForwardToExecute(
                    forwardData.releaseName, forwardData.phaseName, forwardData.environmentName, forwardData.priority,
                    forwardData.getDueDate(), forwardData.assigneeName, forwardData.assignToMe, forwardData.comment);
            Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
            softAssert.assertTrue(taskRepositoryPage.getMessageTest().equals("Zadanie zostało przekazane do wykonania."));

            //Step 14. Select Tasks menu item
            cockpitPage.setActiveMenuElement("Zadania");
            TasksPage tasksPage = new TasksPage(driver);
            Assert.assertTrue(tasksPage.isCorrectPageLoaded());

            //Step 15. Search for the task on the list
            tasksPage.performSearch(testData.taskName);
            Assert.assertTrue(tasksPage.isTaskFound(testData.taskName));
        }

        //Step 16. Select Task Repository menu item
        cockpitPage.setActiveMenuElement("Baza zadań");
        taskRepositoryPage = new TaskRepositoryPage(driver);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());

        //Step 17. Search for new task on the list
        taskRepositoryPage.performSearch(testData.taskName);
        Assert.assertTrue(taskRepositoryPage.isTaskFound(testData.taskName));

        //Step 18.  Delete task on the list
        taskRepositoryPage.performDeleteTask(testData.taskName);
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie w bazie zadań zostało usunięte."));

    }


    @DataProvider
    public Object[][] getExplorationTaskData() throws FileNotFoundException {
        return JsonUtils.getExplorationTaskData();
    }

    @DataProvider
    public Object[][] getTestCaseTaskData() throws FileNotFoundException {
        return JsonUtils.getTestCaseTaskData();
    }

    @DataProvider
    public Object[][] getOtherTaskData() throws FileNotFoundException {
        return JsonUtils.getOtherTaskData();
    }
}

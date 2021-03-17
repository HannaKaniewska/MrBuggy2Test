package tests;

import businessLayer.ExplorationTaskBL;
import businessLayer.OtherTaskBL;
import businessLayer.TestCaseTaskBL;
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

public class TaskTest extends BaseTest {

    private final Logger log = Logger.getLogger(EnvironmentTest.class);

    @Test(dataProvider = "getExplorationTaskData")
    public void addDeleteExplorationTask(ExplorationTaskBL testData) throws IOException {

        testReport = reports.createTest("Add and delete exploration task");
        SoftAssert softAssert = new SoftAssert();
        String projectName;

        //Step 1. Log in
        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
        testReport.pass("1. Log in", getScreenShot());

        //Prepare project for testing: check if project exists and is active, if not -> skip test
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

        //Step 3. Select Task Repository menu item
        cockpitPage.setActiveMenuElement("Baza zadań");
        TaskRepositoryPage taskRepositoryPage = new TaskRepositoryPage(driver);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        testReport.pass("3. Select Task Repository menu item", getScreenShot());

        //Step 4. Press Add exploratory task button
        AddExplorationTaskPage addExplorationTaskPage = taskRepositoryPage.pressAddExplorationTaskButton();
        Assert.assertTrue(addExplorationTaskPage.isCorrectPageLoaded());
        testReport.pass("4. Press Add exploratory task button", getScreenShot());

        //Step 5, 6. Add new exploratory task
        taskRepositoryPage = addExplorationTaskPage.performAddExplorationTask(testData.taskName,
                testData.taskDuration, testData.testCard);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie eksploracyjne zostało dodane."));
        testReport.pass("5, 6. Add new exploratory task", getScreenShot());

        //Step 7. Search for new task on the list
        taskRepositoryPage.performSearch(testData.taskName);
        Assert.assertTrue(taskRepositoryPage.isTaskFound(testData.taskName));
        testReport.pass("7. Search for new task on the list", getScreenShot());

        //Step 8, 9.  Delete task on the list
        taskRepositoryPage.performDeleteTask(testData.taskName);
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie w bazie zadań zostało usunięte."));
        testReport.pass("8. Delete task on the list", getScreenShot());
    }

    @Test(dataProvider = "getTestCaseTaskData")
    public void addDeleteTestCaseTask(TestCaseTaskBL testData) throws IOException {

        testReport = reports.createTest("Add and delete exploration task");
        SoftAssert softAssert = new SoftAssert();
        String projectName;

        //Step 1. Log in
        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
        testReport.pass("1. Log in", getScreenShot());

        //TODO: add this step to every test
        //Prepare project for testing: check if project exists and is active, if not -> skip test
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

        //Step 3. Select Task Repository menu item
        cockpitPage.setActiveMenuElement("Baza zadań");
        TaskRepositoryPage taskRepositoryPage = new TaskRepositoryPage(driver);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        testReport.pass("3. Select Task Repository menu item", getScreenShot());

        //Step 4. Press Add test case task button
        AddTestCaseTaskPage addTestCaseTaskPage = taskRepositoryPage.pressAddTestCaseTaskButton();
        Assert.assertTrue(addTestCaseTaskPage.isCorrectPageLoaded());
        testReport.pass("4. Press Add testcase task button", getScreenShot());

        //Step 5, 6. Add new test case task
        taskRepositoryPage = addTestCaseTaskPage.performAddTestCaseTask(testData.taskName,
                testData.taskDescription, testData.taskPresuppositions, testData.taskResult);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Przypadek testowy został dodany."));
        testReport.pass("5, 6. Add new test case task", getScreenShot());

        //Step 7. Search for new task on the list
        taskRepositoryPage.performSearch(testData.taskName);
        Assert.assertTrue(taskRepositoryPage.isTaskFound(testData.taskName));
        testReport.pass("7. Search for new task on the list", getScreenShot());

        //Step 8, 9.  Delete task on the list
        taskRepositoryPage.performDeleteTask(testData.taskName);
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie w bazie zadań zostało usunięte."));
        testReport.pass("8. Delete task on the list", getScreenShot());

    }

    @Test(dataProvider = "getOtherTaskData")
    public void addDeleteOtherTask(OtherTaskBL testData) throws IOException {
        testReport = reports.createTest("Add and delete other task");
        SoftAssert softAssert = new SoftAssert();
        String projectName;

        //Step 1. Log in
        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
        testReport.pass("1. Log in", getScreenShot());

        //TODO: add this step to every test
        //Prepare project for testing: check if project exists and is active, if not -> skip test
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

        //Step 3. Select Task Repository menu item
        cockpitPage.setActiveMenuElement("Baza zadań");
        TaskRepositoryPage taskRepositoryPage = new TaskRepositoryPage(driver);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        testReport.pass("3. Select Task Repository menu item", getScreenShot());

        //Step 4. Press Add other task button
        AddOtherTaskPage addOtherTaskPage = taskRepositoryPage.pressAddOtherTaskButton();
        Assert.assertTrue(addOtherTaskPage.isCorrectPageLoaded());
        testReport.pass("4. Press Add other task button", getScreenShot());

        //Step 5, 6. Add new other task
        taskRepositoryPage = addOtherTaskPage.performAddOtherTask(testData.taskName,
                testData.taskDescription);
        Assert.assertTrue(taskRepositoryPage.isCorrectPageLoaded());
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie zostało dodane."));
        testReport.pass("5, 6. Add new other task", getScreenShot());

        //Step 7. Search for new task on the list
        taskRepositoryPage.performSearch(testData.taskName);
        Assert.assertTrue(taskRepositoryPage.isTaskFound(testData.taskName));
        testReport.pass("7. Search for new task on the list", getScreenShot());

        //Step 8, 9.  Delete task on the list
        taskRepositoryPage.performDeleteTask(testData.taskName);
        softAssert.assertTrue(taskRepositoryPage.getMessageTest()
                .equals("Zadanie w bazie zadań zostało usunięte."));
        testReport.pass("8. Delete task on the list", getScreenShot());

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

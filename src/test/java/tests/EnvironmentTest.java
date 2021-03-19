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
import java.io.IOException;

@Listeners(ExtentTestNGTestListener.class)
public class EnvironmentTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void addDeleteEnvironmentTest(EnvironmentBL testData) {
        SoftAssert softAssert = new SoftAssert();
        String projectName;

        //Step 1. Log in
        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());

        //Prepare project for testing: check if project exists and is active, if not -> skip test
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

        //Step 3. Select Environment menu item
        cockpitPage.setActiveMenuElement("Środowiska");
        EnvironmentListPage environmentListPage = new EnvironmentListPage(driver);
        Assert.assertTrue(environmentListPage.isCorrectPageLoaded());

        //Check if environment already exists, if not -> Add new environment
        environmentListPage.performSearch(testData.name);
        if (!environmentListPage.isEnvironmentFound(testData.name)) {

            //Step 4. Press Add environment button
            AddEnvironmentPage addEnvironmentPage = environmentListPage.pressAddEnvironmentButton();
            Assert.assertTrue(addEnvironmentPage.isCorrectPageLoaded());

            //Step 5, 6. Add new environment
            environmentListPage = addEnvironmentPage.performAddEnvironment(testData.name, testData.description);
            Assert.assertTrue(environmentListPage.isCorrectPageLoaded());
            softAssert.assertTrue(environmentListPage.getMessageTest()
                    .equals("Środowisko zostało dodane."));

        }

        //Step 7. Search for new environment on the list
        environmentListPage.performSearch(testData.name);
        Assert.assertTrue(environmentListPage.isEnvironmentFound(testData.name));

        //Step 8. Show details page for the new environment
        EnvironmentDetailsPage environmentDetailsPage = environmentListPage
                .showEnvironmentDetails(testData.name);
        Assert.assertTrue(environmentDetailsPage.isCorrectPageLoaded());

        //Step 9, 10. Delete environment
        environmentListPage = environmentDetailsPage.performDeleteEnvironment();
        softAssert.assertTrue(environmentListPage.getMessageTest()
                .equals("Środowisko zostało usunięte."));
        Assert.assertTrue(environmentListPage.isCorrectPageLoaded());
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getEnvironmentData();
    }

}

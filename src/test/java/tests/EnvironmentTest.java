package tests;

import businessLayer.EnvironmentBL;
import businessLayer.ProjectBL;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddEnvironmentPage;
import pages.EnvironmentDetailsPage;
import pages.EnvironmentListPage;
import partials.TopPanel;
import utils.JsonUtils;
import utils.exceptions.NoSuchEnvironmentException;
import utils.exceptions.NoSuchProjectException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EnvironmentTest extends BaseTest {

    private Logger log = Logger.getLogger(EnvironmentTest.class);

    @Test(dataProvider = "getData")
    public void addDeleteEnvironmentTest(EnvironmentBL environmentData) throws IOException, NoSuchEnvironmentException {
        testReport = reports.createTest("Add and delete environment");
        SoftAssert softAssert = new SoftAssert();

        //Step 1. Log in
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
        testReport.pass("1. Log in", getScreenShot());

        //Step 2. Select project on the top panel
        cockpitPage.setActiveProject(environmentData.projectName);
        Assert.assertTrue(cockpitPage.isProjectSet(environmentData.projectName));
        testReport.pass("2. Select project on the top panel", getScreenShot());

        //Step 3. Select Environment menu item
        cockpitPage.setActiveMenuElement("Środowiska");
        EnvironmentListPage environmentListPage = new EnvironmentListPage(driver);
        Assert.assertTrue(environmentListPage.isCorrectPageLoaded());
        testReport.pass("3. Select Environment menu item", getScreenShot());

        //Step 4. Press Add environment button
        AddEnvironmentPage addEnvironmentPage = environmentListPage.pressAddEnvironmentButton();
        Assert.assertTrue(addEnvironmentPage.isCorrectPageLoaded());
        testReport.pass("4. Press Add environment button", getScreenShot());

        //Step 5, 6. Add new environment
        environmentListPage = addEnvironmentPage.performAddEnvironment(environmentData.name, environmentData.description);
        Assert.assertTrue(environmentListPage.isCorrectPageLoaded());
        softAssert.assertTrue(environmentListPage.getMessageTest()
                .equals("Środowisko zostało dodane."));
        testReport.pass("5, 6. Add new environment", getScreenShot());

        //Step 7. Search for new environment on the list
        environmentListPage.performSearch(environmentData.name);
        Assert.assertTrue(environmentListPage.isEnvironmentFound(environmentData.name));
        testReport.pass("7. Search for new environment on the list", getScreenShot());

        //Step 8. Enter details page for the new environment
        EnvironmentDetailsPage environmentDetailsPage = environmentListPage
                .showEnvironmentDetails(environmentData.name);
        Assert.assertTrue(environmentDetailsPage.isCorrectPageLoaded());
        testReport.pass("8. Enter details page for the new environment", getScreenShot());

        //Step 9, 10. Delete environment
        environmentListPage = environmentDetailsPage.performDeleteEnvironment();
        softAssert.assertTrue(environmentListPage.getMessageTest()
                .equals("Środowisko zostało usunięte."));
        //TODO: obsłużyć takie wyjątki tak, żeby test się nie sypał tylko kończył porażką
        Assert.assertTrue(environmentListPage.isCorrectPageLoaded());
        testReport.pass("9, 10. Delete environment", getScreenShot());

    }

        @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getEnvironmentData();
    }

}

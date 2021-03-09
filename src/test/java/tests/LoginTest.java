package tests;

import businessLayer.LoginBL;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.JsonUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LoginTest extends BaseTest{

    private final Logger log = Logger.getLogger(LoginTest.class);


    @Test
    public void valid_login_test () throws IOException {
        testReport = reports.createTest("Valid login test");
        testReport.info("Login email: " + validEmail + ", password: " + validPassword);

        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        //Check if Cockpit page is loaded
        try {
            Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
            testReport.pass("Test passed");
        } catch (AssertionError e) {
            testReport.fail("Test failed. Expected page: KOKPIT", getScreenShot());
            throw e;
        }
    }

    @Test(dataProvider = "getData")
    public void invalid_login_test(LoginBL loginData) throws IOException {
        testReport = reports.createTest(loginData.testCase);
        testReport.info("Login email: " + loginData.email + ", password: " + loginData.password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage = loginPage.performInvalidLogin(loginData.email, loginData.password);
        //Check if the error messages are displayed
        try {
            Assert.assertTrue(loginPage.isErrorMessageListVisible(loginData.errorMessageList));
            testReport.pass("Test passed");
        } catch (AssertionError e) {
            testReport.fail("Test failed. Expected message: " + loginData.errorMessageList, getScreenShot());
            throw e;
        }
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getLoginData();
    }
}

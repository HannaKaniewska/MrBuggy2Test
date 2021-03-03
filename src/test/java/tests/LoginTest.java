package tests;

import businessLayer.LoginData;
import utils.exceptions.NoSuchDriverException;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DriverFactory;
import utils.DriverType;
import utils.JsonUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LoginTest extends BaseTest{

    private Logger log = Logger.getLogger(LoginTest.class);
    private LoginPage loginPage;


    @BeforeMethod @Override
    public void setUp() throws NoSuchDriverException {
        //Overrides setUp() method in BaseTest, to exclude login operation
        //Login is performed separately in test methods in this class
        //Here only initialize driver and open the login page
        log.debug("Before method");
        driver = DriverFactory.getDriver(DriverType.CHROM);
        driver.get(loginUrl);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void valid_login_test () throws IOException {
        testReport = reports.createTest("Valid login test");
        testReport.info("Login email: " + validEmail + ", password: " + validPassword);

        cockpitPage = loginPage.performLogin(validEmail, validPassword);
        //Check if Cockpit page is loaded
        try {
            Assert.assertTrue(cockpitPage.isCorrectPageTitle("kokpit"));
            testReport.pass("Test passed");
        } catch (AssertionError e) {
            testReport.fail("Test failed. Expected page title: KOKPIT", getScreenShot());
            throw e;
        }
    }

    @Test(dataProvider = "getData")
    public void invalid_login_test(LoginData data) throws IOException {
        testReport = reports.createTest(data.testCase);
        testReport.info("Login email: " + data.email + ", password: " + data.password);

        loginPage = loginPage.performInvalidLogin(data.email, data.password);
        //Check if the error messages are displayed
        try {
            Assert.assertTrue(loginPage.isErrorMessageListVisible(data.errorMessageList));
            testReport.pass("Test passed");
        } catch (AssertionError e) {
            testReport.fail("Test failed. Expected message: " + data.errorMessageList, getScreenShot());
            throw e;
        }
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getLoginData();
    }
}

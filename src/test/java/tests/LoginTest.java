package tests;

import businessLayer.LoginBL;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExtentTestNGTestListener;
import utils.JsonUtils;

import java.io.FileNotFoundException;

@Listeners(ExtentTestNGTestListener.class)
public class LoginTest extends BaseTest{


    @Test
    public void valid_login_test () {

        LoginPage loginPage = new LoginPage(driver);
        cockpitPage = loginPage.performLogin(loginEmail, loginPassword);
        //Check if Cockpit page is loaded
        Assert.assertTrue(cockpitPage.isCorrectPageLoaded());
    }

    @Test(dataProvider = "getData")
    public void invalid_login_test(LoginBL loginData) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage = loginPage.performInvalidLogin(loginData.email, loginData.password);
        //Check if the error messages are displayed
        Assert.assertTrue(loginPage.isErrorMessageListVisible(loginData.errorMessageList), "Expected: " + loginData.errorMessageList);
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return JsonUtils.getLoginData();
    }
}

package tests;

import businessLayer.LoginBL;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import tests.testData.UserDataProvider;
import utils.TestListener;

import java.io.FileNotFoundException;

@Listeners(TestListener.class)
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
        loginPage = loginPage.performInvalidLogin(loginData.getEmail(), loginData.getPassword());
        //Check if the error messages are displayed
        Assert.assertTrue(loginPage.isErrorMessageListVisible(loginData.getErrorMessageList()), "Expected: " + loginData.getErrorMessageList());
    }

    @DataProvider
    public Object[][] getData() throws FileNotFoundException {
        return UserDataProvider.getLoginData();
    }
}

package pages;

import utils.SeleniumHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

public class LoginPage {

    @FindBy (id = "email")
    private WebElement emailInput;

    @FindBy (id = "password")
    private WebElement passwordInput;

    @FindBy (id = "login")
    private WebElement loginButton;

    @FindBy (className = "login_form_error")
    private List<WebElement> loginFormErrorMessageList;


    private SeleniumHelper helper;
    private WebDriver driver;
    private Logger log = Logger.getLogger(LoginPage.class);


    public LoginPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
    }

    public LoginPage setEmail (String email) {
        log.info("Setting e-mail");
        emailInput.click();
        emailInput.sendKeys(email);
        return this;
    }

    public LoginPage setPassword (String password) {
        log.info("Setting password");
        passwordInput.click();
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage clickLogin() {
        loginButton.click();
        return this;
    }

    public CockpitPage performLogin (String email, String password) {
        //perform login with valid credentials
        setEmail(email)
            .setPassword(password)
            .clickLogin();
        log.info("Login action performed");
        return new CockpitPage(driver);
    }

    public LoginPage performInvalidLogin (String email, String password) {
        //perform login with invalid credentials and return list of error messages
        setEmail(email)
            .setPassword(password)
            .clickLogin();
        log.info("Invalid login action performed");
        return this;
    }

    public Boolean isErrorMessageListVisible(List<String> expectedErrorMessageList) {
        List<String> errorMessageList = new ArrayList<>();
        for (WebElement element : loginFormErrorMessageList) {
            errorMessageList.add(element.getText());
        }
        return errorMessageList.containsAll(expectedErrorMessageList);
    }

}

package pages;

import utils.FluentWaitHelper;
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


    private final FluentWaitHelper waitHelper;
    private final WebDriver driver;
    private final Logger log = Logger.getLogger(LoginPage.class);


    public LoginPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.waitHelper = new FluentWaitHelper(driver);
    }

    public LoginPage setEmail (String email) {
        log.info("Setting e-mail");
        waitHelper.waitForElementToBeDisplayed(emailInput);
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

    public void clickLogin() {
        loginButton.click();
    }

    public CockpitPage performLogin (String email, String password) {
        //perform login with valid credentials
        this.setEmail(email)
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

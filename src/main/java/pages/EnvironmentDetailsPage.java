package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.DialogBox;
import partials.BasePage;
import utils.SeleniumHelper;

public class EnvironmentDetailsPage extends BasePage {

    @FindBy (xpath = "//a[@class='button_link j_delete_environment']")
    private WebElement deleteButton;

    private final DialogBox dialogBox;

    public EnvironmentDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(EnvironmentDetailsPage.class);
        this.pageTitle = "Środowisko";
        By deleteDialogBoxLocator = By.id("j_popup_delete_environment");
        dialogBox = new DialogBox(driver, deleteDialogBoxLocator);
    }

    public EnvironmentListPage performDeleteEnvironment() {
        deleteButton.click();
        dialogBox.confirmDeleteDialogMessage();
        return new EnvironmentListPage(driver);
    }

}

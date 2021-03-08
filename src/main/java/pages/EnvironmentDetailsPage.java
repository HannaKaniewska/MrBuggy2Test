package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.TopPanel;
import utils.SeleniumHelper;

public class EnvironmentDetailsPage extends TopPanel {

    @FindBy (xpath = "//a[@class='button_link j_delete_environment']")
    private WebElement deleteButton;

    @FindBy (xpath = "//div[@class='ui-dialog-buttonset']//span[text()='Tak']")
    private WebElement yesDialogButton;

    public EnvironmentDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(EnvironmentDetailsPage.class);
        this.pageTitle = "Środowisko";
    }

    public void confirmDeleteDialogMessage() {
        helper.waitForElementToBeDisplayed(By.id("j_popup_delete_environment"));
        yesDialogButton.click();
    }

    public EnvironmentListPage performDeleteEnvironment() {
        deleteButton.click();
        confirmDeleteDialogMessage();
        return new EnvironmentListPage(driver);
    }

}

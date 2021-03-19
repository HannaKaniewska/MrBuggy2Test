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

public class ReleaseDetailsPage extends BasePage {

    @FindBy(xpath = "//a[@class='j_delete_release button_link']")
    private WebElement deleteButton;

    private final DialogBox dialogBox;

    public ReleaseDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(ReleaseDetailsPage.class);
        this.pageTitle = "Wydanie";
        By deleteReleaseMessageBoxLocator = By.id("j_popup_delete_release");
        dialogBox = new DialogBox(driver, deleteReleaseMessageBoxLocator);
    }

    public ReleaseListPage performDeleteRelease() {
        deleteButton.click();
        dialogBox.confirmDeleteDialogMessage();
        return new ReleaseListPage(driver);
    }

}

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

public class PhaseDetailsPage extends BasePage {

    @FindBy(xpath = "//a[@class='j_delete_phase button_link']")
    private WebElement deleteButton;

    private final DialogBox dialogBox;

    public PhaseDetailsPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(PhaseDetailsPage.class);
        this.pageTitle = "Faza";
        By deleteDialogMessageBoxLocator = By.id("j_popup_delete_phase");
        dialogBox = new DialogBox(driver, deleteDialogMessageBoxLocator);
    }

    public PhaseListPage performDeletePhase() {
        deleteButton.click();
        dialogBox.confirmDeleteDialogMessage();
        return new PhaseListPage(driver);
    }

}

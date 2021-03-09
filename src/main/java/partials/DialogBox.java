package partials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.SeleniumHelper;

public class DialogBox {

    @FindBy(xpath = "//div[@class='ui-dialog-buttonset']//span[text()='Tak']")
    private WebElement yesDialogButton;

    private final WebDriver driver;
    private final SeleniumHelper helper;
    private final By dialogBoxLocator;

    public DialogBox(WebDriver driver, By dialogBoxLocator) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.dialogBoxLocator = dialogBoxLocator;
        this.helper = new SeleniumHelper(driver);
    }

    public void confirmDeleteDialogMessage() {
        helper.waitForElementToBeDisplayed(dialogBoxLocator);
        yesDialogButton.click();
    }

}

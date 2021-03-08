package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.TopPanel;
import utils.SeleniumHelper;

public class AddEnvironmentPage extends TopPanel {

    @FindBy (id = "name")
    private WebElement environmentNameInput;

    @FindBy (id = "description")
    private WebElement environmentDescriptionInput;

    @FindBy (id = "save")
    private WebElement saveButton;


    public AddEnvironmentPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(EnvironmentListPage.class);
        this.pageTitle = "Dodaj środowisko";
    }

    public EnvironmentListPage performAddEnvironment(String name, String description) {
        environmentNameInput.click();
        environmentNameInput.sendKeys(name);
        environmentDescriptionInput.click();
        environmentDescriptionInput.sendKeys(description);
        saveButton.click();
        return new EnvironmentListPage(driver);
    }

}

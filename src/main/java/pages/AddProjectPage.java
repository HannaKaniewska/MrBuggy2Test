package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.TopPanel;
import utils.SeleniumHelper;

public class AddProjectPage extends TopPanel {

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy (id = "prefix")
    private WebElement prefixInput;

    @FindBy (id = "save")
    private WebElement saveButton;

    public AddProjectPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(AddProjectPage.class);
        this.pageTitle = "Dodaj projekt";
    }

    public ProjectListPage performAddProject(String name, String prefix) {
        nameInput.click();
        nameInput.sendKeys(name);
        prefixInput.click();
        prefixInput.sendKeys(prefix);
        saveButton.click();
        return new ProjectListPage(driver);
    }
}

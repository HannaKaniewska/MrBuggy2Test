package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.TopPanel;
import utils.SeleniumHelper;

public class AddOtherTaskPage extends TopPanel {
    @FindBy (id = "name")
    private WebElement taskNameInput;

    @FindBy (id = "description")
    private WebElement taskDescriptionInput;

    @FindBy (id = "add")
    private WebElement saveButton;

    public AddOtherTaskPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(AddOtherTaskPage.class);
        this.pageTitle = "Dodaj inne zadanie";
    }

    private void setTaskName (String name) {
        taskNameInput.click();
        taskNameInput.sendKeys(name);
    }

    private void setTaskDescription(String description) {
        taskDescriptionInput.click();
        taskDescriptionInput.sendKeys(description);
    }

    public TaskRepositoryPage performAddOtherTask (String taskName, String taskDescription) {
        setTaskName(taskName);
        setTaskDescription(taskDescription);
        saveButton.click();
        return new TaskRepositoryPage(driver);
    }
}

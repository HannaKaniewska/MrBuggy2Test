package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.BasePage;
import utils.SeleniumHelper;

public class AddExplorationTaskPage extends BasePage {

    @FindBy (id = "name")
    private WebElement taskNameInput;

    @FindBy (id = "duration")
    private WebElement taskDurationInput;

    @FindBy (id = "testCard")
    private WebElement testCardInput;

    @FindBy (id = "add")
    private WebElement saveButton;


    public AddExplorationTaskPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(AddExplorationTaskPage.class);
        this.pageTitle = "Dodaj zadanie eksploracyjne";
    }

    private void setTaskName(String taskName) {
        taskNameInput.click();
        taskNameInput.sendKeys(taskName);
    }

    private void setTaskDuration(String taskDuration) {
        taskDurationInput.click();
        taskDurationInput.sendKeys(taskDuration);
    }

    private void setTestCard(String testCard) {
        testCardInput.click();
        testCardInput.sendKeys(testCard);
    }

    public TaskRepositoryPage performAddExplorationTask(String taskName, String taskDuration, String testCard) {
        setTaskName(taskName);
        setTaskDuration(taskDuration);
        setTestCard(testCard);
        saveButton.click();
        return new TaskRepositoryPage(driver);
    }
}

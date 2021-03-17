package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.TopPanel;
import utils.SeleniumHelper;

public class AddTestCaseTaskPage extends TopPanel {

    @FindBy (id = "name")
    private WebElement taskNameInput;

    @FindBy (id = "description")
    private WebElement taskDescriptionInput;

    @FindBy (id = "presuppositions")
    private WebElement taskPresuppositionsInput;

    @FindBy (id = "result")
    private WebElement taskResultInput;

    @FindBy (id = "add")
    private WebElement saveButton;


    public AddTestCaseTaskPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(AddTestCaseTaskPage.class);
        this.pageTitle = "Dodaj przypadek testowy";
    }

    private void setTaskName(String taskName) {
        taskNameInput.click();
        taskNameInput.sendKeys(taskName);
    }

    private void setTaskDescription(String description) {
        taskDescriptionInput.click();
        taskDescriptionInput.sendKeys(description);
    }

    private void setTaskPresuppositions(String presuppositions) {
        taskPresuppositionsInput.click();
        taskPresuppositionsInput.sendKeys(presuppositions);
    }

    private void setTaskResult(String result) {
        taskResultInput.click();
        taskResultInput.sendKeys(result);
    }

    public TaskRepositoryPage performAddTestCaseTask(String taskName, String taskDescription,
                                                     String taskPresuppositions, String taskResult) {
        setTaskName(taskName);
        setTaskDescription(taskDescription);
        setTaskPresuppositions(taskPresuppositions);
        setTaskResult(taskResult);
        saveButton.click();
        return new TaskRepositoryPage(driver);
    }
}

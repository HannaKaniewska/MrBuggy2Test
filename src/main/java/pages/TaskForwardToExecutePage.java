package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import partials.BasePage;
import utils.SeleniumHelper;

import java.util.Objects;

public class TaskForwardToExecutePage extends BasePage {

    @FindBy (id = "releaseName")
    private WebElement releaseNameInput;

    @FindBy (id = "phaseName")
    private WebElement phaseNameInput;

    @FindBy (id = "token-input-environments")
    private WebElement environmentsInput;

    @FindBy (id = "priority")
    private WebElement priorityInput;

    @FindBy (id = "dueDate")
    private WebElement dueDateInput;

    @FindBy (id = "assigneeName")
    private WebElement assigneeNameInput;

    @FindBy (id = "j_assignToMe")
    private WebElement assingToMeLink;

    @FindBy (id = "comment")
    private WebElement commentInput;

    @FindBy (id = "forward")
    private WebElement submitButton;

    public TaskForwardToExecutePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(TaskForwardToExecutePage.class);
        this.pageTitle = "Przekaż zadanie do wykonania";
    }

    private void setReleaseName(String releaseName) {
        if (!Objects.equals(releaseName, "")) {
            releaseNameInput.click();
            releaseNameInput.sendKeys(releaseName);
            releaseNameInput.sendKeys(Keys.ARROW_DOWN);
            releaseNameInput.sendKeys(Keys.ENTER);
        }
    }

    private void setPhaseName(String phaseName) {
        if (!phaseName.isEmpty()) {
            phaseNameInput.click();
            phaseNameInput.sendKeys(phaseName);
            phaseNameInput.sendKeys(Keys.ARROW_DOWN);
            phaseNameInput.sendKeys(Keys.ENTER);
        }
    }

    private void setEnvironment(String environmentName) {
        if (!environmentName.isEmpty()) {
            environmentsInput.click();
            environmentsInput.sendKeys(environmentName);
            environmentsInput.sendKeys(Keys.ARROW_DOWN);
            environmentsInput.sendKeys(Keys.ENTER);
        }
    }

    private void setPriority(String priority) {
        if (!priority.isEmpty()) {
            Select prioritySelect = new Select(priorityInput);
            prioritySelect.selectByVisibleText(priority);
        }
    }

    private void setDueDate(String dueDate) {
        if (!dueDate.isEmpty()) {
            dueDateInput.click();
            dueDateInput.sendKeys(dueDate);
        }
    }

    private void setAssigneeName(String assigneeName, String assignToMe) {
        if (assignToMe.equals("1") || assignToMe.equals("true")) {
            assingToMeLink.click();
        }
        else if (!assigneeName.isEmpty()) {
            assigneeNameInput.click();
            assigneeNameInput.sendKeys(assigneeName);
            assigneeNameInput.sendKeys(Keys.ARROW_DOWN);
            assigneeNameInput.sendKeys(Keys.ENTER);
        }
    }

    private void setComment(String comment) {
        commentInput.click();
        commentInput.sendKeys(comment);
    }

    public TaskRepositoryPage performTaskForwardToExecute(String releaseName, String phaseName, String environmentName, String priority, String dueDate, String assigneeName, String assignToMe, String comment) {
        setReleaseName(releaseName);
        setPhaseName(phaseName);
        setEnvironment(environmentName);
        setPriority(priority);
        setDueDate(dueDate);
        setAssigneeName(assigneeName, assignToMe);
        setComment(comment);
        submitButton.click();
        return new TaskRepositoryPage(driver);
    }

}

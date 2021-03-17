package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.DialogBox;
import partials.SearchPanel;
import partials.TopPanel;
import utils.SeleniumHelper;

public class TaskRepositoryPage extends TopPanel {

    //Buttons panel
    @FindBy (xpath = "//a[@class='open button_link']")
    private WebElement addTaskButton;

    @FindBy (xpath = "//a[text()='Eksploracja']")
    private WebElement addExplorationTaskButton;

    @FindBy (xpath = "//a[text()='Przypadek testowy']")
    private WebElement addTestCaseTaskButton;

    @FindBy (xpath = "//a[text()='Inne']")
    private WebElement addOtherTaskButton;

    private final By deleteTaskMessageBoxLocator = By.id("j_popup_delete_task");
    private final SearchPanel searchPanel;
    private final DialogBox dialogBox;

    public TaskRepositoryPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(TaskRepositoryPage.class);
        this.pageTitle = "Baza zadań";
        this.searchPanel = new SearchPanel(driver);
        dialogBox = new DialogBox(driver, deleteTaskMessageBoxLocator);

    }

    private void pressAddTaskButton() {
        addTaskButton.click();
        helper.waitForElementToBeDisplayed(By.className("button_link_li"));
    }

    public AddExplorationTaskPage pressAddExplorationTaskButton() {
        pressAddTaskButton();
        addExplorationTaskButton.click();
        return new AddExplorationTaskPage(driver);
    }

    public AddTestCaseTaskPage pressAddTestCaseTaskButton() {
        pressAddTaskButton();
        addTestCaseTaskButton.click();
        return new AddTestCaseTaskPage(driver);
    }

    public AddOtherTaskPage pressAddOtherTaskButton() {
        pressAddTaskButton();
        addOtherTaskButton.click();
        return new AddOtherTaskPage(driver);
    }

    public void performSearch(String searchText) {
        searchPanel.performSearch(searchText);
    }

    public boolean isTaskFound(String taskName) {
        return (searchPanel.getItemFromResultsTable(taskName) != null);
    }

    public void performDeleteTask(String taskName) {
        WebElement row = searchPanel.getRowFromResultsTable(taskName);
        WebElement actionButton = row.findElement(By.id("action_icon"));
        actionButton.click();
        WebElement deleteOption = row.findElement(By.className("j_delete_task"));
        helper.waitForElementToBeDisplayed(deleteOption);
        deleteOption.click();
        dialogBox.confirmDeleteDialogMessage();
    }
}

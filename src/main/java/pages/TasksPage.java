package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import partials.SearchPanel;
import partials.BasePage;
import utils.SeleniumHelper;

public class TasksPage extends BasePage {

    private final SearchPanel searchPanel;

    public TasksPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(TasksPage.class);
        this.pageTitle = "Zadania";
        this.searchPanel = new SearchPanel(driver);
    }

    public void performSearch(String searchText) {
        searchPanel.performSearch(searchText);
    }

    public boolean isTaskFound(String taskName) {
        return (searchPanel.getItemFromResultsTable(taskName) != null);
    }
}

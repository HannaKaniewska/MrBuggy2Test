package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.SearchPanel;
import partials.BasePage;
import utils.ProjectStatus;
import utils.SeleniumHelper;

public class ProjectListPage extends BasePage {

    //Buttons panel
    @FindBy (xpath = "//a[text()='Dodaj projekt']")
    private WebElement addProjectButton;

    //Results table
    private final By projectStatusLocator = By.xpath("//td[@class='t_status']");

    private final SearchPanel searchPanel;


    public ProjectListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(ProjectListPage.class);
        this.pageTitle = "Projekty";
        this.searchPanel = new SearchPanel(driver);
    }

    public ProjectListPage performSearch(String searchText) {
        searchPanel.performSearch(searchText);
        return this;
    }


    public boolean isProjectFound(String projectName) {
        return(searchPanel.getItemFromResultsTable(projectName) != null);
    }

    public boolean isProjectFound() {
        return (searchPanel.getFirstItemFromResultsTable() != null);
    }

    public ProjectStatus getProjectStatus() {
        //find status of the first project in the result list
        ProjectStatus status = null;
        String statusText = driver.findElement(projectStatusLocator).getText();
        switch (statusText) {
            case "Aktywny" -> status = ProjectStatus.ACTIVE;
            case "Zakończony" -> status = ProjectStatus.CLOSED;
            case "Zawieszony" -> status = ProjectStatus.SUSPENDED;
        }
        return status;
    }

    public AddProjectPage pressAddProjectButton() {
        addProjectButton.click();
        return new AddProjectPage(driver);
    }

    public ProjectDetailsPage showProjectDetails(String projectName) {
        searchPanel.showItemDetails(projectName);
        return new ProjectDetailsPage(driver);
    }

    public String getFirstProjectName() {
        WebElement firstItem = searchPanel.getFirstItemFromResultsTable();
        return firstItem.getText();
    }
}

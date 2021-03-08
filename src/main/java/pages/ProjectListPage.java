package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.SearchPanel;
import partials.TopPanel;
import utils.ProjectStatus;
import utils.SeleniumHelper;
import utils.exceptions.NoSuchProjectException;

import java.util.ArrayList;
import java.util.List;

public class ProjectListPage extends TopPanel {

    //Buttons panel
    @FindBy (xpath = "//a[text()='Dodaj projekt']")
    private WebElement addProjectButton;

    //Results table
    private By resultsTableLocator = By.xpath("//section[@id='content']//table");
    private By projectStatusLocator = By.xpath("//td[@class='t_status']");
    private By projectNameLocator = By.xpath("//section[@id='content']//table//td/a");

    private SearchPanel searchPanel;


    public ProjectListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(ProjectListPage.class);
        this.pageTitle = "Projekty";
        this.searchPanel = new SearchPanel(driver);
    }

    public void performSearch(String searchText) {
        searchPanel.performSearch(searchText);
    }


    public boolean isProjectFound(String projectName) {
        return(getProjectFromResultsTable(projectName) != null);
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

    private WebElement getProjectFromResultsTable(String projectName) {
        List<WebElement> projectList = driver.findElements(projectNameLocator);
        for (WebElement project : projectList) {
            if (project.getText().equals(projectName)) {
                return project;
            }
        }
        return null;
    }

    public ProjectDetailsPage showProjectDetails(String expectedProjectName) throws NoSuchProjectException {
        WebElement projectItem = getProjectFromResultsTable(expectedProjectName);
        if (projectItem != null) {
            projectItem.click();
        } else {
            throw new NoSuchProjectException();
        }
        return new ProjectDetailsPage(driver);
    }
}

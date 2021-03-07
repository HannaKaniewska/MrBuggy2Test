package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.TopPanel;
import utils.ProjectStatus;
import utils.SeleniumHelper;
import utils.exceptions.NoSuchProjectException;

import java.util.List;

public class ProjectListPage extends TopPanel {

    //Search panel
    @FindBy (id = "search")
    private WebElement searchInput;

    @FindBy (id = "j_filterButton")
    private WebElement searchButton;

    //Buttons panel
    @FindBy (xpath = "//a[text()='Dodaj projekt']")
    private WebElement addProjectButton;

    //Results table
    private By resultsTableLocator = By.xpath("//section[@id='content']//table");
    private By projectStatusLocator = By.xpath("//td[@class='t_status']");
    private By projectNameLocator = By.xpath("//section[@id='content']//table//td/a");


    public ProjectListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(ProjectListPage.class);
        this.pageTitle = "Projekty";
    }

    public ProjectListPage performSearchForProject(String searchText) {
        searchInput.click();
        searchInput.sendKeys(searchText);
        searchButton.click();
        return this;
    }

    public boolean isProjectFound() {
        return driver.findElements(resultsTableLocator).size() > 0;
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

    public ProjectDetailsPage enterProjectDetails (String expectedProjectName) throws NoSuchProjectException {
        List<WebElement> projectList = driver.findElements(projectNameLocator);
        boolean projectFound = false;
        for (WebElement project : projectList) {
            if (project.getText().equals(expectedProjectName)) {
                project.click();
                projectFound = true;
            }
        }
        if (! projectFound) {
            throw new NoSuchProjectException();
        }
        return new ProjectDetailsPage(driver);
    }
}

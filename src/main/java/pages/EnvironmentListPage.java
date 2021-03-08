package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.SearchPanel;
import partials.TopPanel;
import utils.SeleniumHelper;
import utils.exceptions.NoSuchEnvironmentException;
import utils.exceptions.NoSuchProjectException;

import java.util.List;

public class EnvironmentListPage extends TopPanel {

    //Buttons panel
    @FindBy(xpath = "//a[text()='Dodaj środowisko']")
    private WebElement addEnvironmentButton;

    //Message Box
    @FindBy(id = "j_info_box")
    private WebElement messageBox;

    //Results table
    private By resultsTableLocator = By.xpath("//section[@id='content']//table");
    private By environmentNameLocator = By.xpath("//section[@id='content']//table//td/a");


    private SearchPanel searchPanel;


    public EnvironmentListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(EnvironmentListPage.class);
        this.pageTitle = "Środowiska";
        this.searchPanel = new SearchPanel(driver);
    }

    public AddEnvironmentPage pressAddEnvironmentButton() {
        addEnvironmentButton.click();
        return new AddEnvironmentPage(driver);
    }

    public String getMessageTest() {
        helper.waitForElementToBeDisplayed(messageBox);
        return messageBox.getText();
    }

    public void performSearch(String searchText) {
        searchPanel.performSearch(searchText);
    }

    //TODO: move results methods to Search class
    public boolean isEnvironmentFound(String environmentName) {
        return (getEnvironmentFromResultsTable(environmentName) != null);
    }

    private WebElement getEnvironmentFromResultsTable(String environmentName) {
        List<WebElement> environmentList = driver.findElements(environmentNameLocator);
        for (WebElement item : environmentList) {
            if (item.getText().equals(environmentName)) {
                return item;
            }
        }
        return null;
    }

    public EnvironmentDetailsPage showEnvironmentDetails(String expectedEnvironmentName) throws NoSuchEnvironmentException {
        WebElement environmentItem = getEnvironmentFromResultsTable(expectedEnvironmentName);
        if (environmentItem != null) {
            environmentItem.click();
        } else {
            throw new NoSuchEnvironmentException();
        }
        return new EnvironmentDetailsPage(driver);
    }
}

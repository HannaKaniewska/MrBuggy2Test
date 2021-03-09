package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.SearchPanel;
import partials.TopPanel;
import utils.SeleniumHelper;

public class EnvironmentListPage extends TopPanel {

    //Buttons panel
    @FindBy(xpath = "//a[text()='Dodaj środowisko']")
    private WebElement addEnvironmentButton;

    private final SearchPanel searchPanel;


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

    public void performSearch(String searchText) {
        searchPanel.performSearch(searchText);
    }

    public boolean isEnvironmentFound(String environmentName) {
        return (searchPanel.getItemFromResultsTable(environmentName) != null);
    }

    public EnvironmentDetailsPage showEnvironmentDetails(String environmentName) {
        searchPanel.showItemDetails(environmentName);
        return new EnvironmentDetailsPage(driver);
    }
}

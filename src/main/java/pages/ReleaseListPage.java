package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.SearchPanel;
import partials.BasePage;
import utils.SeleniumHelper;

public class ReleaseListPage extends BasePage {

    //Buttons panel
    @FindBy(xpath = "//a[text()='Dodaj wydanie']")
    private WebElement addReleaseButton;

    private final SearchPanel searchPanel;

    public ReleaseListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(ReleaseListPage.class);
        this.pageTitle = "Wydania";
        this.searchPanel = new SearchPanel(driver);
    }

    public AddReleasePage pressAddReleaseButton() {
        addReleaseButton.click();
        return new AddReleasePage(driver);
    }

    public void performSearch(String searchText) {
        searchPanel.performSearch(searchText);
    }

    public boolean isReleaseFound(String releaseName) {
        return (searchPanel.getItemFromResultsTable(releaseName) != null);
    }

    public ReleaseDetailsPage showReleaseDetails(String releaseName) {
        searchPanel.showItemDetails(releaseName);
        return new ReleaseDetailsPage(driver);
    }

}

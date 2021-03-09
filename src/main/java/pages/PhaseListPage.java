package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.SearchPanel;
import partials.TopPanel;
import utils.SeleniumHelper;

public class PhaseListPage extends TopPanel {

    //Buttons panel
    @FindBy(xpath = "//a[text()='Dodaj fazę']")
    private WebElement addPhaseButton;

    private final SearchPanel searchPanel;

    public PhaseListPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(PhaseListPage.class);
        this.pageTitle = "Fazy";
        this.searchPanel = new SearchPanel(driver);
    }

    public AddPhasePage pressAddPhaseButton() {
        addPhaseButton.click();
        return new AddPhasePage(driver);
    }

    public void performSearch(String searchText) {
        searchPanel.performSearch(searchText);
    }

    public boolean isPhaseFound(String phaseName) {
        return (searchPanel.getItemFromResultsTable(phaseName) != null);
    }

    public PhaseDetailsPage showPhaseDetails(String phaseName) {
        searchPanel.showItemDetails(phaseName);
        return new PhaseDetailsPage(driver);
    }


}

package partials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchPanel {

    @FindBy(id = "search")
    public WebElement searchInput;

    @FindBy (id = "j_filterButton")
    public WebElement searchButton;

    //Results table
    private final By resultsTableLocator = By.xpath("//section[@id='content']//table");
    private final By nameLocator = By.xpath("//section[@id='content']//table//td/a");

    private final WebDriver driver;

    public SearchPanel (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void performSearch(String searchText) {
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(searchText);
        searchButton.click();
    }

    public WebElement getItemFromResultsTable(String name) {
        List<WebElement> itemList = driver.findElements(nameLocator);
        for (WebElement item : itemList) {
            if (item.getText().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public void showItemDetails(String itemName) {
        WebElement item = getItemFromResultsTable(itemName);
        if (item != null) {
            item.click();
        }
    }



}

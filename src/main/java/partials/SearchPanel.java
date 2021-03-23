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
    public WebElement getFirstItemFromResultsTable() {
        List<WebElement> resultsTableList = driver.findElements(resultsTableLocator);
        if (resultsTableList.size() == 0) {
            return null;
        }
        List<WebElement> resultsRows = resultsTableList.get(0).findElements(By.xpath("//tbody/tr"));
        return resultsRows.get(0).findElement(By.xpath("//td/a"));
    }

    public WebElement getRowFromResultsTable(String name) {
        List<WebElement> resultsTableList = driver.findElements(resultsTableLocator);
        if (resultsTableList.size() > 0) {
            List<WebElement> resultsRows = resultsTableList.get(0).findElements(By.xpath("//tbody/tr"));
            for (WebElement row : resultsRows) {
                WebElement item = row.findElement(By.xpath("//td/a"));
                if (item.getText().equals(name)) {
                    return row;
                }
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

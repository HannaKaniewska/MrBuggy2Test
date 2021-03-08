package partials;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPanel {

    @FindBy(id = "search")
    public WebElement searchInput;

    @FindBy (id = "j_filterButton")
    public WebElement searchButton;

    public SearchPanel (WebDriver driver) {
        PageFactory.initElements(driver, this);
        //this.driver = driver;
        //this.helper = new SeleniumHelper(driver);
    }

    public void performSearch(String searchText) {
        searchInput.click();
        searchInput.sendKeys(searchText);
        searchButton.click();
    }

}

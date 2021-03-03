package pages;

import utils.SeleniumHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Locale;

public class CockpitPage {

    @FindBy (xpath = "//h1[@class='content_title']")
    private WebElement pageTitle;

    private SeleniumHelper helper;
    private WebDriver driver;
    private Logger log = Logger.getLogger(LoginPage.class);

    public CockpitPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
    }

    public Boolean isCorrectPageTitle (String expectedPageTitle) {
        return pageTitle.getText().toLowerCase(Locale.ROOT).equals(expectedPageTitle);
    }

}

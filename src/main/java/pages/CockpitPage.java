package pages;

import partials.TopPanel;
import utils.SeleniumHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Locale;

public class CockpitPage extends TopPanel {

    public CockpitPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        this.log = Logger.getLogger(CockpitPage.class);
        this.pageTitle = "Kokpit";
    }

}

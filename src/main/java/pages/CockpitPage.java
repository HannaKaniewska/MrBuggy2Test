package pages;

import utils.FluentWaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CockpitPage extends BasePage {

    public CockpitPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new FluentWaitHelper(driver);
        this.log = Logger.getLogger(CockpitPage.class);
        this.pageTitle = "Kokpit";
    }

}

package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.FluentWaitHelper;

import java.util.Locale;

public class BasePage {

    //Top panel
    @FindBy (xpath = "//h1[@class='content_title']")
    protected WebElement pageTitleSection;


    protected FluentWaitHelper helper;
    protected WebDriver driver;
    protected Logger log;
    protected String pageTitle;


    public boolean isCorrectPageLoaded() {
        return (pageTitleSection.getText().toLowerCase(Locale.ROOT)
                .equals(pageTitle.toLowerCase(Locale.ROOT)));
    }

}

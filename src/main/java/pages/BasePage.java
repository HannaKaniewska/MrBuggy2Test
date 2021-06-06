package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.CockpitPage;
import utils.SeleniumHelper;

import java.util.List;
import java.util.Locale;

public class BasePage {

    //Top panel
    @FindBy (xpath = "//div[@class='header_admin']//a[text()='Administracja']")
    private WebElement administrationLink;

    @FindBy (xpath = "//div[@class='header_admin']//a[text()='Wyjdź z administracji']")
    private WebElement leaveAdministrationLink;

    @FindBy (xpath = "//h1[@class='content_title']")
    protected WebElement pageTitleSection;

    //Menu section
    @FindBy (xpath = "//a[@class='activeMenu']")
    protected WebElement activeMenuElement;

    @FindBy (id = "left_header_menu")
    protected WebElement leftMenuSection;

    //Set active project section
    @FindBy (id = "activeProject_chosen")
    protected WebElement activeProjectLink;

    @FindBy (xpath = "//div[@class='chosen-search']//input")
    protected WebElement searchActiveProjectInput;

    //Message Box
    @FindBy(id = "j_info_box")
    protected WebElement messageBox;


    protected SeleniumHelper helper;
    protected WebDriver driver;
    protected Logger log;
    protected String pageTitle;


    public boolean isCorrectPageLoaded() {
        return (pageTitleSection.getText().toLowerCase(Locale.ROOT)
                .equals(pageTitle.toLowerCase(Locale.ROOT)));
    }

}

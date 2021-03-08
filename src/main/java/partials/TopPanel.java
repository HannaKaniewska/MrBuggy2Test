package partials;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.CockpitPage;
import pages.ProjectListPage;
import utils.SeleniumHelper;

import java.util.Locale;

public class TopPanel {

    //Top panel
    @FindBy (xpath = "//div[@class='header_admin']//a[text()='Administracja']")
    private WebElement administrationLink;

    @FindBy (xpath = "//div[@class='header_admin']//a[text()='Wyjdź z administracji']")
    private WebElement leaveAdministrationLink;

    @FindBy (xpath = "//h1[@class='content_title']")
    protected WebElement pageTitleSection;

    //Menu panel
    @FindBy (xpath = "//a[@class='activeMenu']")
    protected WebElement activeMenuElement;

    //Set active project panel
    @FindBy (id = "activeProject_chosen")
    protected WebElement activeProjectLink;

    @FindBy (xpath = "//div[@class='chosen-search']//input")
    protected WebElement searchActiveProjectInput;

    protected SeleniumHelper helper;
    protected WebDriver driver;
    protected Logger log;
    protected String pageTitle;


    public ProjectListPage enterAdminPanel() {
        administrationLink.click();
        return new ProjectListPage(driver);
    }

    public CockpitPage leaveAdminPanel() {
        leaveAdministrationLink.click();
        return new CockpitPage(driver);
    }

    public boolean isCorrectPageLoaded() {
        return (pageTitleSection.getText().toLowerCase(Locale.ROOT)
                .equals(pageTitle.toLowerCase(Locale.ROOT)));
    }

    public boolean isMenuItemActive(String menuItem) {
        return (activeMenuElement.getText().toLowerCase(Locale.ROOT)
                .equals(menuItem.toLowerCase(Locale.ROOT)));
    }

    public TopPanel setActiveProject(String projectName) {
        activeProjectLink.click();
        searchActiveProjectInput.click();
        searchActiveProjectInput.sendKeys(projectName);
        searchActiveProjectInput.sendKeys(Keys.ARROW_DOWN);
        searchActiveProjectInput.sendKeys(Keys.ENTER);
        return this;
    }

    public boolean isProjectSet(String projectName) {
        return (activeProjectLink.getText().toLowerCase(Locale.ROOT)
            .equals(projectName.toLowerCase(Locale.ROOT)));
    }
}

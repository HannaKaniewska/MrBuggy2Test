package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.BasePage;
import utils.SeleniumHelper;

public class AddReleasePage extends BasePage {

    @FindBy(id = "name")
    private WebElement releaseNameInput;

    @FindBy (id = "startDate")
    private WebElement releaseStartDateInput;

    @FindBy (id = "endDate")
    private WebElement releaseEndDateInput;

    @FindBy (id = "description")
    private WebElement releaseDescriptionInput;

    @FindBy (id = "save")
    private WebElement saveButton;


    public AddReleasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(AddReleasePage.class);
        this.pageTitle = "Dodaj wydanie";
    }

    public void setDate(WebElement dateInput, String dateValue) {
        dateInput.click();
        dateInput.sendKeys(dateValue);
        helper.waitForElementToBeDisplayed(By.id("ui-datepicker-div"));
        dateInput.sendKeys(Keys.ESCAPE);
    }

    public ReleaseListPage performAddRelease(String name, String startDate, String endDate, String description) {
        releaseNameInput.click();
        releaseNameInput.sendKeys(name);
        if (!startDate.isEmpty()) {setDate(releaseStartDateInput, startDate);}
        if (!endDate.isEmpty()) {setDate(releaseEndDateInput, endDate);}
        releaseDescriptionInput.click();
        releaseDescriptionInput.sendKeys(description);
        saveButton.click();
        return new ReleaseListPage(driver);
    }
}

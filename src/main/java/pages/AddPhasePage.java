package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import partials.TopPanel;
import utils.SeleniumHelper;

public class AddPhasePage extends TopPanel {

    @FindBy (id = "name")
    private WebElement phaseNameInput;

    @FindBy (id = "releaseName")
    private WebElement releaseNameInput;

    @FindBy (id = "startDate")
    private WebElement phaseStartDateInput;

    @FindBy (id = "endDate")
    private WebElement phaseEndDateInput;

    @FindBy (id = "description")
    private WebElement phaseDescriptionInput;

    @FindBy (id = "save")
    private WebElement saveButton;

    public AddPhasePage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(AddPhasePage.class);
        this.pageTitle = "Dodaj fazę";
    }

    public void setDate(WebElement dateInput, String dateValue) {
        dateInput.click();
        dateInput.sendKeys(dateValue);
        helper.waitForElementToBeDisplayed(By.id("ui-datepicker-div"));
        dateInput.sendKeys(Keys.ESCAPE);
    }

    public void setRelease(WebElement releaseInput, String releaseName) {
        releaseInput.click();
        releaseInput.sendKeys(releaseName);
        helper.waitForElementToBeDisplayed(By.id("ui-id-1"));
        releaseInput.sendKeys(Keys.ARROW_DOWN);
        releaseInput.sendKeys(Keys.ENTER);
    }

    public PhaseListPage performAddPhase(String phaseName, String releaseName, String phaseStartDate,
                                         String phaseEndDate, String phaseDescription) {
        phaseNameInput.click();
        phaseNameInput.sendKeys(phaseName);
        setRelease(releaseNameInput, releaseName);
        if (!phaseStartDate.isEmpty()) {setDate(phaseStartDateInput, phaseStartDate);}
        if (!phaseEndDate.isEmpty()) {setDate(phaseEndDateInput, phaseEndDate);}
        phaseDescriptionInput.click();
        phaseDescriptionInput.sendKeys(phaseDescription);
        saveButton.click();
        return new PhaseListPage(driver);
    }
}

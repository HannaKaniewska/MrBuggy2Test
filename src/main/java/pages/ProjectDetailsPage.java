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

import java.util.List;
import java.util.Locale;

public class ProjectDetailsPage extends BasePage {

    //Roles in the project
    @FindBy(xpath = "//form[@name='j_editUsersForm']//tbody")
    private WebElement rolesTable;

    private final By rowsLocator = By.tagName("tr");
    private final By roleNameCellLocator = By.xpath("td[@class='t_status']");
    private final By searchUserInputLocator = By.id("token-input-users");
    private final By userCellLocator = By.tagName("span");
    private final By userDropDownLocator = By.xpath("//div[@class='token-input-dropdown-facebook']/ul");
    private final By addUserButtonLocator = By.className("j_editUsers");
    private final By saveButtonLocator = By.className("j_saveUsers");


    public ProjectDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(ProjectDetailsPage.class);
        this.pageTitle = "Właściwości projektu";
    }

    public boolean isUserInProjectRole(String roleName, String userEmail) {
        List<WebElement> rows = rolesTable.findElements(rowsLocator);
        boolean isUserFound = false;
        for (WebElement row: rows) {
            //search for the role with expected role name
            WebElement roleCell = row.findElement(roleNameCellLocator);
            if (roleCell.getText().toLowerCase(Locale.ROOT).equals(roleName.toLowerCase(Locale.ROOT))) {
                //check if the user is already attached to this role
                WebElement usersCell = row.findElement(userCellLocator);
                isUserFound = usersCell.getText().contains(userEmail);
            }
        }
        return isUserFound;
    }

    public ProjectDetailsPage addUserToProjectRole(String roleName, String userEmail) {
        List<WebElement> rows = rolesTable.findElements(rowsLocator);
        for (WebElement row: rows) {
            //search for the role with expected role name
            WebElement roleCell = row.findElement(roleNameCellLocator);
            if (roleCell.getText().toLowerCase(Locale.ROOT).equals(roleName.toLowerCase(Locale.ROOT))) {
                //check if the user is already attached to this role, if it isn't -> add user to this role
                WebElement usersCell = row.findElement(userCellLocator);
                if (!usersCell.getText().contains(userEmail)) {
                    //click "Add/Delete Users" button
                    WebElement addUserButton = row.findElement(addUserButtonLocator);
                    addUserButton.click();
                    //enter user name in search input
                    WebElement searchInput = row.findElement(searchUserInputLocator);
                    searchInput.click();
                    searchInput.sendKeys(userEmail);
                    //choose first item on the drop-down list
                    helper.waitForElementToBeDisplayed(userDropDownLocator);
                    searchInput.sendKeys(Keys.ARROW_DOWN);
                    searchInput.sendKeys(Keys.ENTER);
                    //perform save
                    WebElement saveButton = row.findElement(saveButtonLocator);
                    saveButton.click();
                }
            }
        }
        return this;
    }

}

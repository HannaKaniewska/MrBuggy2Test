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

import java.util.List;
import java.util.Locale;

public class ProjectDetailsPage extends TopPanel {

    //Roles in the project
    @FindBy(xpath = "//form[@name='j_editUsersForm']//tbody")
    private WebElement rolesTable;

    private By roleNameCellLocator = By.xpath("td[@class='t_status']");
    private By searchUserInputLocator = By.id("token-input-users");


    public ProjectDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.helper = new SeleniumHelper(driver);
        log = Logger.getLogger(ProjectListPage.class);
        this.pageTitle = "Właściwości projektu";
    }

    public ProjectDetailsPage addUserToProjectRole(String roleName, String userEmail) {
        List<WebElement> rows = rolesTable.findElements(By.tagName("tr"));
        for (WebElement row: rows) {
            //search for the role with expected role name
            WebElement roleCell = row.findElement(roleNameCellLocator);
            if (roleCell.getText().toLowerCase(Locale.ROOT).equals(roleName.toLowerCase(Locale.ROOT))) {
                //click "Add/Delete Users" button
                WebElement addUserButton = row.findElement(By.className("j_editUsers"));
                addUserButton.click();
                //enter user name in search input
                WebElement searchInput = row.findElement(searchUserInputLocator);
                searchInput.click();
                searchInput.sendKeys(userEmail);
                //choose first item on the drop-down list
                helper.waitForElementToBeDisplayed(By.xpath("//div[@class='token-input-dropdown-facebook']/ul"));
                searchInput.sendKeys(Keys.ARROW_DOWN);
                searchInput.sendKeys(Keys.ENTER);
                //perform save
                WebElement saveButton = row.findElement(By.className("j_saveUsers"));
                saveButton.click();
            }
        }
        return this;
    }

}

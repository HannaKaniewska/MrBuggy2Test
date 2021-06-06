package tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.CockpitPage;
import utils.DriverFactory;
import utils.DriverType;
import utils.exceptions.NoSuchDriverException;

public abstract class BaseTest {
    protected WebDriver driver;
    protected CockpitPage cockpitPage;
    protected final String loginUrl = "http://demo.mrbuggy2.testarena.pl/zaloguj";
    protected final String loginEmail = "admin@tc2014.pl";
    protected final String loginPassword = "12qwAS";



    @BeforeMethod
    public void setUp() throws NoSuchDriverException {
        //initialize driver, login to the app
        driver = DriverFactory.getDriver(DriverType.CHROM);
        driver.get(loginUrl);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        driver.quit();
        DriverFactory.resetDriver();
    }

}

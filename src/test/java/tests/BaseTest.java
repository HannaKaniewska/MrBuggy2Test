package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.CockpitPage;
import utils.DriverFactory;
import utils.DriverType;
import utils.SeleniumHelper;
import utils.exceptions.NoSuchDriverException;

import java.io.IOException;

public abstract class BaseTest {
    protected WebDriver driver;
    protected ExtentHtmlReporter reporter;
    protected ExtentReports reports;
    protected ExtentTest testReport;
    protected CockpitPage cockpitPage;
    protected final String loginUrl = "http://demo.mrbuggy2.testarena.pl/zaloguj";
    protected final String validEmail = "admin@tc2014.pl";
    protected final String validPassword = "12qwAS";
    private final Logger log = Logger.getLogger(BaseTest.class);



    @BeforeTest
    public void setUpReporter() {
        //initialize reporter
        reporter = new ExtentHtmlReporter("src//test//resources//reports//index.html");
        reports = new ExtentReports();
        reports.attachReporter(reporter);
    }

    @BeforeMethod
    public void setUp() throws NoSuchDriverException {
        //initialize driver, login to the app
        log.debug("Before method");
        driver = DriverFactory.getDriver(DriverType.CHROM);
        driver.get(loginUrl);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        log.debug("After test");
        driver.quit();
        DriverFactory.resetDriver();
    }

    @AfterTest
    public void tearDownReporter() {
        reports.flush();
        reporter.flush();
    }

    protected MediaEntityModelProvider getScreenShot() throws IOException {
        return MediaEntityBuilder.createScreenCaptureFromPath(
                SeleniumHelper.takeScreenshot(driver)).build();
    }

}

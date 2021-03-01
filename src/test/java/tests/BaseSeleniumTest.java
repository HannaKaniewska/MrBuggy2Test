package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import exceptions.NoSuchDriverException;
import helpers.SeleniumHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import utils.DriverFactory;
import utils.DriverType;

import java.io.IOException;

public abstract class BaseSeleniumTest {
    protected WebDriver driver;
    protected ExtentHtmlReporter reporter;
    protected ExtentReports reports;
    private Logger log = Logger.getLogger(BaseSeleniumTest.class);

    @BeforeTest
    public void setUpReporter() {
        reporter = new ExtentHtmlReporter("src//test//resources//reports//index.html");
        reports = new ExtentReports();
        reports.attachReporter(reporter);
    }

    @BeforeMethod
    public void setUp() throws NoSuchDriverException {
        log.debug("Before method");
        driver = DriverFactory.getDriver(DriverType.CHROM);
    }

    @AfterMethod
    public void tearDown() {
        log.debug("After test");
        driver.quit(); //zamyka wszystkie okna //driver.close(); - zamyka okno, na którym jest focus
        DriverFactory.resetDriver();
    }

    @AfterTest
    public void tearDownReporter() {
        reports.flush();
        reporter.flush();
    }

    protected MediaEntityModelProvider getScreenShot(String filePath) throws IOException {
        return MediaEntityBuilder.createScreenCaptureFromPath(
                SeleniumHelper.takeScreenshot(driver,filePath)).build();
    }

}

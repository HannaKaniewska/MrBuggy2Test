package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import utils.exceptions.NoSuchDriverException;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class ExtentTestNGTestListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.createInstance("src//test//resources//reports//index.html");
    private static final ThreadLocal parentTest = new ThreadLocal();
    private static final ThreadLocal test = new ThreadLocal();

    final Logger log = Logger.getLogger(ExtentTestNGTestListener.class);

    public void onStart(ITestContext context) {
        log.debug("On start");
        ExtentTest test = extent.createTest("Tests");
        parentTest.set(test);
    }


    public void onTestStart(ITestResult result) {
        log.debug("On test start" + result.getInstanceName() + " - " + result.getName());
        //nowa klasa, pobrać jej nazwę (albo nazwę testu)
        ExtentTest child = ((ExtentTest)parentTest.get()).createNode(result.getInstanceName() + " - " + result.getName());
        test.set(child);
    }

    public void onTestSuccess(ITestResult result) {
        ((ExtentTest)test.get()).pass("Test passed");
    }

    public void onTestFailure(ITestResult result) {
        MediaEntityModelProvider screenshot;
        try {
            screenshot = MediaEntityBuilder.createScreenCaptureFromPath(
                    SeleniumHelper.takeScreenshot(DriverFactory.getDriver(DriverType.CHROM))).build();
            ((ExtentTest)test.get()).fail(result.getThrowable(), screenshot);
        } catch (IOException | NoSuchDriverException e) {
            log.error(e.getStackTrace());
        }
    }

    public void onTestSkipped(ITestResult result) {
        MediaEntityModelProvider screenshot;
        try {
            screenshot = MediaEntityBuilder.createScreenCaptureFromPath(
                    SeleniumHelper.takeScreenshot(DriverFactory.getDriver(DriverType.CHROM))).build();
            ((ExtentTest)test.get()).skip(result.getThrowable(), screenshot);
        } catch (IOException | NoSuchDriverException e) {
            log.error(e.getStackTrace());
        }
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onFinish(ITestContext context) {
        log.debug("On finish");
        extent.flush();
    }
}

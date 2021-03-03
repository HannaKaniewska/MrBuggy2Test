package utils;

import utils.exceptions.NoSuchDriverException;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class TestListener implements ITestListener {

    Logger log = Logger.getLogger(TestListener.class);

    public void onTestStart(ITestResult result) {
        log.debug("On test start");
    }

    public void onTestSuccess(ITestResult result) {
        log.debug("On test success");
    }

    public void onTestFailure(ITestResult result) {
        try {
            SeleniumHelper.takeScreenshot(DriverFactory.getDriver(DriverType.CHROM));
        } catch (NoSuchDriverException | IOException e) {
            log.error(e.getStackTrace());
        }
        log.debug("On test failure");
    }

    public void onTestSkipped(ITestResult result) {
        log.debug("On test skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.debug("On test failed but within success percentage");
    }

    public void onStart(ITestContext context) {
        log.debug("On start");
    }

    public void onFinish(ITestContext context) {
        log.debug("On finish");

    }
}

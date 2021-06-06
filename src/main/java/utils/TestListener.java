package utils;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    final Logger log = Logger.getLogger(TestListener.class);

    public void onStart(ITestContext context) {
        log.debug("On start");
    }


    public void onTestStart(ITestResult result) {
        log.debug("On test start" + result.getInstanceName() + " - " + result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        log.debug("Test succeeded");
    }

    public void onTestFailure(ITestResult result) {
        log.debug("Test failed: " + result.getThrowable().getStackTrace());
    }

    public void onTestSkipped(ITestResult result) {
        log.debug("Test skipped:" + result.getInstanceName() + " - " + result.getName());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onFinish(ITestContext context) {
        log.debug("Test finished");
    }
}

package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance("src//test//resources//reports//index.html");
        return extent;
    }

    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setReportName(fileName);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
}

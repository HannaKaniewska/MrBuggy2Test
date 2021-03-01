package tests;

import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestClass extends BaseSeleniumTest {

    @Test
    public void googleOpenTest() throws IOException {

        ExtentTest testReport = reports.createTest("Test");
        String screenshotFilePath = "src//test//resources//reports//Test";

        driver.get("https://www.google.com/");
        testReport.info("Koniec testu", getScreenShot(screenshotFilePath));
    }

}

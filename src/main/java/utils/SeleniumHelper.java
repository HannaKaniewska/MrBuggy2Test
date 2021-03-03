package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class SeleniumHelper {

    private WebDriver driver;

    public SeleniumHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementToBeDisplayed(By locator) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.pollingEvery(Duration.ofMillis(1000))
                .withTimeout(Duration.ofSeconds(15))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToBeDisplayed(WebElement element) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.pollingEvery(Duration.ofMillis(1000))
                .withTimeout(Duration.ofSeconds(15))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForListOfWebElements(List<WebElement> elementList) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.pollingEvery(Duration.ofMillis(1000))
                .withTimeout(Duration.ofSeconds(15))
                .ignoring(NoSuchElementException.class);
        wait.until(driver1 ->
                elementList.size()>0);
    }

    public static String takeScreenshot(WebDriver driver) throws IOException {

        TakesScreenshot screenshoter = (TakesScreenshot) driver;
        File screenshotFile = screenshoter.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File("src/test/resources/reports/" + LocalTime.now().getNano() +".png");
        Files.copy(screenshotFile.toPath(), destinationFile.toPath());
        return destinationFile.getName();
    }

}

package utils;

import utils.exceptions.NoSuchDriverException;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;

public class DriverFactory {
    private static WebDriver driverInstance;
    private static final Logger log = Logger.getLogger(DriverFactory.class);

    public static WebDriver getDriver(DriverType driverType) throws NoSuchDriverException {
        if (driverInstance == null) {
            getSpecificDriver(driverType);
            Dimension dimension = new Dimension(1280, 800);
            driverInstance.manage().window().setSize(dimension);
        }
        return driverInstance;
    }

    private static void getSpecificDriver(DriverType driverType) throws NoSuchDriverException {

        switch (driverType) {
            case CHROM -> {
                File chromeExe = new File("src//main//resources//drivers//chromedriver 4");
                ChromeDriverService chromeService = new ChromeDriverService.Builder()
                        .usingDriverExecutable(chromeExe)
                        .usingAnyFreePort()
                        .build();
                driverInstance = new ChromeDriver(chromeService);
            }
            case FIREFOX -> {
                File firefoxExe = new File("src//main//resources//drivers//geckodriver");
                GeckoDriverService firefoxService = new GeckoDriverService.Builder()
                        .usingDriverExecutable(firefoxExe)
                        .usingAnyFreePort()
                        .build();
                driverInstance = new FirefoxDriver(firefoxService);
            }
            default -> {
                log.error("Brak drivera danego typu: " + driverType);
                throw new NoSuchDriverException();
            }
        }
    }

    public static void resetDriver() {
        driverInstance = null;
    }

}

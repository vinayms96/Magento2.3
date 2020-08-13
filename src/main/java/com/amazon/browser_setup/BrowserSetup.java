package com.amazon.browser_setup;

import com.amazon.mysql.JdbcConnection;
import com.amazon.loggers.Loggers;
import com.amazon.path.Constants;
import com.amazon.utilities.ExcelUtils;
import com.amazon.utilities.Property;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BrowserSetup implements Constants {
    public WebDriver driver = null;

    /*
     * Executing all the Pre Test Run methods in @BeforeSuite
     * */
    @BeforeSuite
    public void preTestRun() {
        ExcelUtils.excelConfigure(excel_path);
        JdbcConnection.establishConnection();
    }

    /*
     * Invoke the Browser specified as System Argument (Chrome or Firefox)
     * Also selecting Browser Modes (Headless or not)
     *   off -> Headless
     * */
    @BeforeTest
    public void setup() {
        // Setting Browser Capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setAcceptInsecureCerts(true);

        // Setting Browser Options
        ChromeOptions ch_options = new ChromeOptions();
        ch_options.merge(capabilities);

        FirefoxOptions ff_options = new FirefoxOptions();
        ff_options.merge(capabilities);

        // Setting Browser Mode
        if (System.getProperty("head").equalsIgnoreCase("off")) {
            ch_options.addArguments("--headless");
            ff_options.addArguments("--headless");
        }

        // Selecting the Browser
        if (System.getProperty("browser").equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (System.getProperty("browser").equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        // Hitting the URL and Maximizing the window
        driver.get(Property.getProperty("url"));
        driver.manage().window().maximize();
    }

    /*
     * Closing the Browser after the end of each Test
     * */
    @AfterTest
    public void finish() {
        driver.quit();
    }

    @AfterSuite
    public void postTestRun() {
        try {
            JdbcConnection.getConnection().close();
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }

}
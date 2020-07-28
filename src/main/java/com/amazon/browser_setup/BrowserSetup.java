package com.amazon.browser_setup;

import com.amazon.utilities.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.amazon.path.Constants;
import com.amazon.utilities.Property;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserSetup implements Constants {
    public WebDriver driver = null;

    /*
        Executing all the Pre Test Run methods in @BeforeSuite
     */
    @BeforeSuite
    public void pre_test_run() {
        ExcelUtils.excel(excel_path);
    }

    @BeforeTest
    public void setup() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setAcceptInsecureCerts(true);

        ChromeOptions ch_options = new ChromeOptions();
        ch_options.merge(capabilities);

        FirefoxOptions ff_options = new FirefoxOptions();
        ff_options.merge(capabilities);

        if (System.getProperty("head").equalsIgnoreCase("off")) {
            ch_options.addArguments("--headless");
            ff_options.addArguments("--headless");
        }

        if (System.getProperty("browser").equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (System.getProperty("browser").equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.get(Property.get_Property("url"));
        driver.manage().window().maximize();
    }

    @AfterTest
    public void finish() {
        driver.quit();
    }

}
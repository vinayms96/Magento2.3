package com.magento.browser_setup;

import com.magento.extent_reports.ExtentReport;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.mysql.DatabaseSampleData;
import com.magento.mysql.JdbcConnection;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
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

    /**
     * Executing all the Pre Test Run methods in @BeforeSuite
     */
    @BeforeSuite
    public void preTestRun() {
        /*Setting the Loggers*/
        Loggers.setLogger(BrowserSetup.class.getName());

        /*Configuring the Excel Data*/
        ExcelUtils.excelConfigure(EXCEL_TEST_PATH);
        ExcelUtils.getRowData(1);
        System.out.println(ExcelUtils.getLastCellNumber());

        /*Configuring the Database Connection*/
        JdbcConnection.establishConnection();

        /*Add full or Update the database from Excel*/
        String property = Property.getProperty("updateData");

        switch (property) {
            case "table":
                /*Creating the table and data*/
                DatabaseSampleData.createTable();
                break;
            case "update":
                /*Updating the table and data*/
                ExcelUtils.excelConfigure(EXCEL_UPDATE_PATH);
                DatabaseSampleData.updateTable();
                ExcelUtils.excelConfigure(EXCEL_TEST_PATH);
                break;
            default:
                break;
        }

        /*configuring the Extent Reports*/
        ExtentReport.extentReport();
    }

    /**
     * Invoke the Browser specified as System Argument (Chrome or Firefox)
     * Also selecting Browser Modes (Headless or not)
     * off -> Headless
     */
    @BeforeTest
    public void setup() {
        /*Setting Browser Capabilities*/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setAcceptInsecureCerts(true);

        /*Setting Browser Options*/
        ChromeOptions ch_options = new ChromeOptions();
        ch_options.merge(capabilities);

        FirefoxOptions ff_options = new FirefoxOptions();
        ff_options.merge(capabilities);

        /*Setting Browser Mode*/
        if (Property.getProperty("head").equalsIgnoreCase("off")) {
            ch_options.addArguments("--headless");
            ff_options.addArguments("--headless");
        }

        /*Selecting the Browser*/
        if (Property.getProperty("browser").equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            Loggers.getLogger().info("Chrome browser is Launched");
        } else if (Property.getProperty("browser").equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            Loggers.getLogger().info("Firefox browser is Launched");
        }

        /*Hitting the URL and Maximizing the window*/
        driver.manage().window().maximize();
        driver.get(Property.getProperty("url"));
        Loggers.getLogger().info("Website Url is hit");
    }

    /**
     * Closing the Browser after the end of each Test
     */
    @AfterTest
    public void finish() {
        driver.quit();
        Loggers.getLogger().info("Browser is closed");
    }

    /**
     * Executing all the Post Test Run methods in @AfterSuite
     */
    @AfterSuite
    public void postTestRun() {
        /*Closing the Database Connection*/
        try {
            JdbcConnection.getConnection().close();
            Loggers.getLogger().info("Database connection is closed.");
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }

        /*Flushing the Extent Reports to generate the report*/
        ExtentReport.getExtentReports().flush();
        Loggers.getLogger().info("Extent Report is flushed and report is created");
    }

}
package com.magento.browser_setup;

import com.magento.extent_reports.ExtentReport;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.mail.SMTPMail;
import com.magento.mysql.DatabaseSampleData;
import com.magento.mysql.JdbcConnection;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BrowserSetup implements Constants {
    public static WebDriver driver;
    private DesiredCapabilities capabilities;
    private ChromeOptions ch_options;
    private FirefoxOptions ff_options;

    /**
     * Executing all the Pre Test Run methods in @BeforeSuite
     */
    @BeforeSuite(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRun() {

        /*Setting the Loggers*/
        Loggers.setLogger(BrowserSetup.class.getName());

        /*configuring the Extent Reports*/
        ExtentReport.extentReport();

        /*Configuring the Excel Data*/
        ExcelUtils.excelConfigure(EXCEL_TEST_PATH);

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

    }

    /**
     * Invoke the Browser specified as System Argument (Chrome or Firefox)
     * Also selecting Browser Modes (Headless or not)
     * off -> Headless
     */
    @BeforeMethod(description = "Browser setup configurations", alwaysRun = true)
    public void setup() {
        /*Setting Browser Capabilities*/
        capabilities = new DesiredCapabilities();
        capabilities.setAcceptInsecureCerts(true);

        /*Setting Browser Options*/
        ch_options = new ChromeOptions();
        ch_options.merge(capabilities);

        ff_options = new FirefoxOptions();
        ff_options.merge(capabilities);

        /*Setting Browser Mode*/
        if (Property.getProperty("headless").equalsIgnoreCase("on")) {
            ch_options.addArguments("--headless");
            ff_options.addArguments("--headless");
        }

        /*Selecting the Browser*/
        if (Property.getProperty("browser").equalsIgnoreCase("Chrome")) {
//            WebDriverManager.chromedriver().setup();
            System.setProperty("webdriver.chrome.driver", "./../magento_data/browser_drivers/chromedriver");
            driver = new ChromeDriver(ch_options);
            Loggers.getLogger().info("Chrome browser is Launched");
        } else if (Property.getProperty("browser").equalsIgnoreCase("Firefox")) {
//            WebDriverManager.firefoxdriver().setup();
            System.setProperty("webdriver.gecko.driver", "./../magento_data/browser_drivers/geckodriver");
            driver = new FirefoxDriver(ff_options);
            Loggers.getLogger().info("Firefox browser is Launched");
        }

        /*Hitting the URL and Maximizing the window*/
        driver.manage().window().maximize();
        driver.get(Property.getProperty("url"));

        try {
            Assert.assertEquals(driver.getTitle(), "Home Page");
            Loggers.getLogger().info("Website Url is hit");
        } catch (Exception e) {
            Loggers.getLogger().error("Could not launch the website");
        }

    }

    /**
     * Closing the Browser after the end of each Test
     */
    @AfterMethod(description = "Post Test configurations", alwaysRun = true)
    public void finish() {
        driver.quit();
        Loggers.getLogger().info("Browser is closed");
    }

    /**
     * Executing all the Post Test Run methods in @AfterSuite
     */
    @AfterSuite(description = "Final finish configurations", alwaysRun = true)
    public void postTestRun() {
        /*Closing the Database Connection*/
        try {
            JdbcConnection.getConnection().close();
            Loggers.getLogger().info("Database connection is closed.");
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }

        /*Flushing the Extent Reports to generate the report*/
        if (Property.getProperty("extent").equalsIgnoreCase("enable")) {
            ExtentReport.getExtentReports().flush();
//            SMTPMail.sendEmail();
            Loggers.getLogger().info("Extent Report is flushed and report is created");
        }
    }

}
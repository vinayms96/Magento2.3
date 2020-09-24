package com.magento.extent_reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.pickers.DatePicker;
import com.magento.utilities.Property;

public class ExtentReport implements Constants {
    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> extentNodeThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> extentSubNodeThreadLocal = new ThreadLocal<>();
    private static ExtentSparkReporter sparkReporter;
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private static ExtentTest extentNode;
    private static ExtentTest extentSubNode;
    private static String dateTime;

    /**
     * Setting up the Extent Report with Reporter
     */
    public static void extentReport() {

        // Setting the Loggers
        Loggers.setLogger(ExtentReport.class.getName());

        // Picking the Date and Time
        dateTime = DatePicker.getDateTime();

        /*Defining the ype of reporter
         * And specifying the interfaces to save the report*/
        sparkReporter = new ExtentSparkReporter(EXTENT_PATH + dateTime + ".html");

        // Loading the Extent Configuration XML file
        sparkReporter.loadXMLConfig(EXTENT_CONFIG_PATH);
        Loggers.getLogger().info("Extent report config file is loaded");

        // Defining the ExtentReports and attaching the above reporter
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        // Setting System Environment
        setSystemInformation();
        Loggers.getLogger().info("Reporter attached to reports");

    }

    /**
     * Setting the System Environment for Extent Reports
     */
    public static void setSystemInformation() {

        extentReports.setSystemInfo("Selenium Version", Property.getProperty("seleniumVersion"));
        extentReports.setSystemInfo("Environment", Property.getProperty("environ"));
        extentReports.setSystemInfo("Browser", Property.getProperty("browser"));
        extentReports.setSystemInfo("Contributors", Property.getProperty("contributor"));
        Loggers.getLogger().info("Extent environments are set");

    }

    /**
     * Creating the Test using the extentReports reference
     *
     * @param testName
     */
    public static void createTest(String testName) {
        extentTest = extentReports.createTest(testName);
        extentTestThreadLocal.set(extentTest);
    }

    /**
     * Creating the node using the extentTest reference
     *
     * @param nodeName
     */
    public static void createNode(String nodeName) {
        extentNode = extentTestThreadLocal.get().createNode(nodeName);
        extentNodeThreadLocal.set(extentNode);
    }

    /**
     * Creating the Sub Node using the extentNode reference
     *
     * @param subNodeName
     */
    public static void createSubNode(String subNodeName) {
        extentSubNode = extentNodeThreadLocal.get().createNode(subNodeName);
        extentSubNodeThreadLocal.set(extentTest);
    }

    /**
     * @return extentReports
     */
    public static ExtentReports getExtentReports() {
        return extentReports;
    }

    /**
     * @return extentTest
     */
    public static ExtentTest getExtentTest() {
        return extentTestThreadLocal.get();
    }

    /**
     * @return extentNode
     */
    public static ExtentTest getExtentNode() {
        return extentNodeThreadLocal.get();
    }

    /**
     * @return extentSubNode
     */
    public static ExtentTest getExtentSubNode() {
        return extentSubNodeThreadLocal.get();
    }

    /**
     * @return - Date Time String
     */
    public static String getDateTime() {
        return dateTime;
    }

}

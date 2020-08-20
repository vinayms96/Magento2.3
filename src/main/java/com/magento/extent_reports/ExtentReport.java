package com.magento.extent_reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.modules.DatePicker;

public class ExtentReport implements Constants {
    private static ExtentSparkReporter sparkReporter;
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private static ExtentTest extentNode;

    /**
     * Setting up the Extent Report with Reporter
     */
    public static void extentReport() {

        /*Setting the Loggers*/
        Loggers.setLogger(ExtentReport.class.getName());

        /*Picking the Date and Time*/
        String dateTime = DatePicker.getDateTime();

        /*Defining the ype of reporter
         * And specifying the interfaces to save the report*/
        sparkReporter = new ExtentSparkReporter(EXTENT_PATH + dateTime + ".html");

        /*Loading the Extent Configuration XML file*/
        sparkReporter.loadXMLConfig(EXTENT_CONFIG_PATH);
        Loggers.getLogger().info("Extent report config file is loaded");

        /*Defining the ExtentReports and attaching the above reporter*/
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        Loggers.getLogger().info("Reporter attached to reports");
    }

    /**
     * Creating the Test using the extentReports reference
     *
     * @param testName
     */
    public static void createTest(String testName) {
        extentTest = extentReports.createTest(testName);
    }

    /**
     * Creating the node using the extentTest reference
     *
     * @param nodeName
     */
    public static void createNode(String nodeName) {
        extentNode = extentTest.createNode(nodeName);
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
        return extentTest;
    }

    /**
     * @return extentNode
     */
    public static ExtentTest getExtentNode() {
        return extentNode;
    }

}

package com.magento.extent_reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.magento.modules.DatePicker;

public class ExtentReport {
    private static ExtentSparkReporter sparkReporter;
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private static ExtentTest extentNode;

    /**
     * Setting up the Extent Report with Reporter
     */
    public static void extentReport() {

        /*Picking the Date and Time*/
        String dateTime = DatePicker.getDateTime();

        /*Defining the ype of reporter
        * And specifying the path to save the report*/
        sparkReporter = new ExtentSparkReporter("./../magento_data/Reports/Test Report " + dateTime + ".html");

        /*Loading the Extent Configuration XML file*/
        sparkReporter.loadXMLConfig("./src/test/Resources/extent-config.xml");

        /*Defining the ExtentReports and attaching the above reporter*/
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    /**
     * Creating the Test using the extentReports reference
     * @param testName
     */
    public static void createTest(String testName) {
        extentTest = extentReports.createTest(testName);
    }

    /**
     * Creating the node using the extentTest reference
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

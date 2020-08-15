package com.magento;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.path.Constants;
import com.magento.utilities.ExcelUtils;

import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest implements Constants {
    /**
     * Rigourous Test :-)
     */
//    @Test
    public static void main(String[] args) throws SQLException {
        Loggers.setLogger();
        ExcelUtils.excelConfigure(excel_path);
        ExcelUtils.getRowData(1);
        System.out.println(ExcelUtils.getLastCellNumber());
        System.out.println(ExcelUtils.getDataMap().get("password"));
        Loggers.getLogger().info("Test");
        ExtentReport.extentReport();
        ExtentReport.createTest("New Test");
        ExtentReport.createNode("Node11");
        ExtentReport.getExtentNode().pass("Node1 Pass");
        ExtentReport.createNode("Node12");
        ExtentReport.getExtentNode().fail("Node2 Fail");
        ExtentReport.getExtentTest().pass("Extent test Pass");
        ExtentReport.getExtentReports().flush();
//        JdbcConnection.establishConnection();
//        if(JdbcConnection.getConnection() != null){
//            System.out.println(JdbcConnection.getConnection().getClientInfo());
//        }
//        DynamicPath.getScreenshotPath();
//        System.out.println(Screenshot.getScreenshotBase64());
        System.out.println("Completed");
    }

}

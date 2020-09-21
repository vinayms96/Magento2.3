package com.magento;

import com.aventstack.extentreports.ExtentTest;
import com.magento.extent_reports.ExtentReport;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.mail.SMTPMail;
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
    public static void main(String[] args) throws SQLException, InterruptedException {
        Loggers.setLogger(AppTest.class.getName());
        ExtentReport.extentReport();
        ExcelUtils.excelConfigure(EXCEL_TEST_PATH);
        ExcelUtils.getRowData(1);
        System.out.println(ExcelUtils.getLastCellNumber());
        System.out.println(ExcelUtils.getDataMap().get("password"));
        System.out.println(ExcelUtils.getDataMap().get("first_name"));
//        Loggers.getLogger().info("Test");
        ExtentReport.createTest("New Test");
        ExtentReport.getExtentTest().pass("Mail Test Successful");
        Thread.sleep(15);
        ExtentReport.getExtentReports().flush();
        SMTPMail.sendEmail();
//        ExtentReport.createNode("Node11");
//        ExtentReport.getExtentNode().pass("Node1 Pass");
//        ExtentReport.createNode("Node12");
//        ExtentReport.getExtentNode().fail("Node2 Fail");
//        ExtentReport.getExtentTest().pass("Extent test Pass");
//        ExtentReport.getExtentReports().flush();
//        JdbcConnection.establishConnection();
//        ExcelUtils.excelConfigure(EXCEL_UPDATE_PATH);
//        DatabaseSampleData.updateTable();

//        DatabaseSampleData.test();

//        if(JdbcConnection.getConnection() != null){
//            System.out.println(JdbcConnection.getConnection().getClientInfo());
//        }
//        ResultSet result = DatabaseUtils.getData("SELECT * FROM customer_data WHERE id=1;");
//        System.out.println(result);
//        for (int i = 1; i <= DatabaseUtils.getResultSetMetaData().getColumnCount(); i++) {
//            System.out.println(result.getString(i));
//        }
//        System.out.println(result.getString(0));
//        DynamicPath.getScreenshotPath();
//        System.out.println(Screenshot.getScreenshotBase64());
        Loggers.getLogger().info("Success");
        System.out.println("Completed");
    }
    /*@Test
    public void test() {
        Loggers.setLogger(AppTest.class.getName());
//        System.out.println(DatabaseUtils.getData("SELECT * FROM customer_data;"));
        Loggers.getLogger().info("Completed in AppTest");
//        Logger log = LogManager.getLogger(AppTest.class.getName());
//        log.info("Tested");
//        System.out.println("Completed");
    }*/

}

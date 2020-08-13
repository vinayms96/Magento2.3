package com.amazon;

import com.amazon.mysql.JdbcConnection;
import com.amazon.loggers.Loggers;
import com.amazon.path.Constants;
import com.amazon.utilities.ExcelUtils;

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
        JdbcConnection.establishConnection();
        if(JdbcConnection.getConnection() != null){
            System.out.println(JdbcConnection.getConnection().getClientInfo());
        }
        System.out.println("Completed");
    }

}

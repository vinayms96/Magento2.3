package com.amazon;

import com.amazon.modules.Loggers;
import com.amazon.path.Constants;
import com.amazon.utilities.ExcelUtils;

/**
 * Unit test for simple App.
 */
public class AppTest implements Constants {
    /**
     * Rigourous Test :-)
     */
//    @Test
    public static void main(String[] args) {
        Loggers.setLogger();
        ExcelUtils.excel(excel_path);
        ExcelUtils.getRowData(1);
        System.out.println(ExcelUtils.getLastCellNumber());
        System.out.println(ExcelUtils.getDataMap().get("email_id"));
        Loggers.getLogger().info("Test");
        System.out.println("Completed");
    }

}

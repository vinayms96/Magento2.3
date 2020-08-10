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
        ExcelUtils.get_row_data(1);
        System.out.println(ExcelUtils.get_last_cell_num());
        System.out.println(ExcelUtils.getData_map().get("email_id"));
        Loggers.getLogger().info("Test");
        System.out.println("Completed");
    }
}

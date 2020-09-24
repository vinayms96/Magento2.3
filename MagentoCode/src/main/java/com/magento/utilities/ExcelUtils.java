package com.magento.utilities;

import com.magento.loggers.Loggers;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ExcelUtils {
    private static XSSFWorkbook work_book;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static ArrayList<String> cell_headers;
    private static HashMap<String, String> data_map;
    private static DataFormatter formatter;

    /**
     * This method runs in the beginning of the Project execution as set in @BeforeSuite Annotation
     * This method declares the Workbook and stores all the headers to ArrayList<String>
     *
     * @param path
     */
    public static void excelConfigure(String path) {
        cell_headers = new ArrayList<>();
        data_map = new HashMap<>();
        formatter = new DataFormatter();

        try {
            // Declaring the Workbook by passing the interfaces to Excel file
            work_book = new XSSFWorkbook(new FileInputStream(new File(path)));

            // Get the sheet at index 0
            sheet = work_book.getSheetAt(0);

            // Iterate through the Columns and store the Headers in ArrayList<String>
            Iterator<Row> first_row = sheet.iterator();
            Iterator<Cell> cell_iter = first_row.next().cellIterator();
            while (cell_iter.hasNext()) {
                cell_headers.add(cell_iter.next().getStringCellValue());
            }
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }

    /**
     * Fetch the entire row data and map it to HashMap<String, String>
     *
     * @param row_num
     */
    public static void getRowData(int row_num) {
        for (int i = 0; i < getLastCellNumber(); i++) {
            data_map.put(cell_headers.get(i), formatter.formatCellValue(sheet.getRow(row_num).getCell(i)));
        }
        Loggers.getLogger().info("Row " + row_num + " data is fetched and mapped");
    }

    /**
     * Fetch the last row number from the Excel Sheet
     * And return the Integer
     *
     * @return int
     */
    public static int getLastRowNumber() {
        return sheet.getLastRowNum();
    }

    /**
     * Fetch the last cell number from the Excel Sheet
     * And return the Integer
     *
     * @return int
     */
    public static int getLastCellNumber() {
        return sheet.getRow(0).getLastCellNum();
    }

    /**
     * Return the Cell Headers list from ArrayList<String>
     *
     * @return ArrayList<String>
     */
    public static ArrayList<String> getCellHeaders() {
        return cell_headers;
    }

    /**
     * Return the Data that is Mapped from HashMap<String,String>
     *
     * @return HashMap<String, String>
     */
    public static HashMap<String, String> getDataMap() {
        return data_map;
    }
}

package com.amazon.utilities;

import org.apache.poi.ss.usermodel.Cell;
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
    private static XSSFWorkbook wbook;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static ArrayList<String> cell_headers;
    private static HashMap<String, String> data_map;

    /*
        This method runs in the beginning of the Project execution as set in @BeforeSuite Annotation
        This method declares the Workbook and stores all the headers to ArrayList<String>
     */
    public static void excel(String path) {
        cell_headers = new ArrayList<>();
        data_map = new HashMap<>();

        try {
            // Declaring the Workbook by passing the path to Excel file
            wbook = new XSSFWorkbook(new FileInputStream(new File(path)));

            // Get the sheet at index 0
            sheet = wbook.getSheetAt(0);

            // Iterate through the Columns and store the Headers in ArrayList<String>
            Iterator<Row> first_row = sheet.iterator();
            Iterator<Cell> cell_iter = first_row.next().cellIterator();
            while (cell_iter.hasNext()) {
                cell_headers.add(cell_iter.next().getStringCellValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void get_row_data(int row_num) {
        for (int i = 1; i <= get_last_cell_num(); i++) {
            data_map.put(cell_headers.get(i), sheet.getRow(row_num).getCell(i).getStringCellValue());
        }
    }

    /*
       Fetch the last row number from the Excel Sheet
       And return the Integer
    */
    public static int get_last_row_num() {
        return sheet.getLastRowNum();
    }

    /*
        Fetch the last cell number from the Excel Sheet
        And return the Integer
     */
    public static int get_last_cell_num() {
        return sheet.getRow(0).getLastCellNum();
    }

    /*
        Return the Cell Headers list from ArrayList<String>
     */
    public static ArrayList<String> getCell_headers() {
        return cell_headers;
    }

    /*
        Return the Data that is Mapped from HashMap<String,String>
     */
    public static HashMap<String, String> getData_map() {
        return data_map;
    }
}

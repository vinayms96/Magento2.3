package com.magento.mysql;

import com.magento.loggers.Loggers;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseSampleData {
    private static Connection no_connect;
    private static String database_name;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    /**
     * Creating Sample Database
     * With Sample Table and Values
     */
    public static void createData(String JDBC_DRIVER, String NO_DATABASE_URL, String USERNAME, String PASSWORD) {

        database_name = Property.getProperty("database_name");

        /*Creating Sample database*/
        try {

            /*Setting new connection without Database name*/
            Class.forName(JDBC_DRIVER);
            no_connect = DriverManager.getConnection(NO_DATABASE_URL, USERNAME, PASSWORD);

            /*Creating Database*/
            statement = no_connect.createStatement();
            String database_name_query = "CREATE DATABASE " + database_name + ";";
            statement.executeUpdate(database_name_query);
            Loggers.getLogger().info("Sample Database Created");

            /*Re-Establishing connection after creating new database*/
            JdbcConnection.establishConnection();
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }

        /*Creating Sample Table and Values*/
        Connection table_connect = JdbcConnection.getConnection();
        String database_table_1 = Property.getProperty("database_table_1");

        /*Fetching the Column Headers*/
        String cell_0 = ExcelUtils.getCellHeaders().get(0);
        String cell_1 = ExcelUtils.getCellHeaders().get(1);
        String cell_2 = ExcelUtils.getCellHeaders().get(2);
        String cell_3 = ExcelUtils.getCellHeaders().get(3);
        String cell_4 = ExcelUtils.getCellHeaders().get(4);
        String cell_5 = ExcelUtils.getCellHeaders().get(5);
        String cell_6 = ExcelUtils.getCellHeaders().get(6);
        String cell_7 = ExcelUtils.getCellHeaders().get(7);
        String cell_8 = ExcelUtils.getCellHeaders().get(8);
        String cell_9 = ExcelUtils.getCellHeaders().get(9);

        try {
            /*Creating the Column Headers*/
            statement = table_connect.createStatement();
            String create_table = "CREATE TABLE " + database_table_1 + " (" +
                    cell_0 + " INT AUTO_INCREMENT NOT NULL," +
                    cell_1 + " VARCHAR(256)," +
                    cell_2 + " VARCHAR(256)," +
                    cell_3 + " VARCHAR(256)," +
                    cell_4 + " VARCHAR(256)," +
                    cell_5 + " VARCHAR(256)," +
                    cell_6 + " VARCHAR(256)," +
                    cell_7 + " VARCHAR(256)," +
                    cell_8 + " VARCHAR(256)," +
                    cell_9 + " INT," +
                    "PRIMARY KEY (" + cell_0 + "));";

            /*Executing the Statement to create Column Headers*/
            statement.executeUpdate(create_table);
            Loggers.getLogger().info("Table with the name '" + database_table_1 + "' is created");

            /*Adding multiple row data to the Database Table*/
            for (int row = 1; row <= ExcelUtils.getLastRowNumber(); row++) {
                /*Fetching each row data*/
                ExcelUtils.getRowData(row);

                /*Creating the Table values*/
                preparedStatement = table_connect.prepareStatement("INSERT INTO " + database_table_1 + "(" +
                        cell_1 + "," + cell_2 + "," + cell_3 + "," + cell_4 + "," + cell_5 + "," + cell_6 + "," + cell_7 + "," +
                        cell_8 + "," + cell_9 + ")" + " VALUES " + "(?,?,?,?,?,?,?,?,?);");

                /*Setting the Column values by fetching it from Excel*/
                preparedStatement.setString(1, ExcelUtils.getDataMap().get(cell_1));
                preparedStatement.setString(2, ExcelUtils.getDataMap().get(cell_2));
                preparedStatement.setString(3, ExcelUtils.getDataMap().get(cell_3));
                preparedStatement.setLong(4, Long.parseLong(ExcelUtils.getDataMap().get(cell_4)));
                preparedStatement.setString(5, ExcelUtils.getDataMap().get(cell_5));
                preparedStatement.setString(6, ExcelUtils.getDataMap().get(cell_6));
                preparedStatement.setString(7, ExcelUtils.getDataMap().get(cell_7));
                preparedStatement.setString(8, ExcelUtils.getDataMap().get(cell_8));
                preparedStatement.setInt(9, Integer.parseInt(ExcelUtils.getDataMap().get(cell_9)));

                /*Executing the PreparedStatement to enter the table values*/
                preparedStatement.executeUpdate();
            }
            Loggers.getLogger().info("Sample data is added to the '" + database_table_1 + "' table");

        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }
}
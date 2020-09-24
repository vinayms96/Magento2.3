package com.magento.mysql;

import com.magento.interfaces.DatabaseHeaders;
import com.magento.loggers.Loggers;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;

import java.sql.*;

public class DatabaseSampleData implements DatabaseHeaders {
    private static Connection no_connect;
    private static String database_name;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static DatabaseMetaData databaseMetaData;
    private static ResultSet resultSet;

    /**
     * Creating Sample Database
     */
    public static void createDatabase(String JDBC_DRIVER, String NO_DATABASE_URL, String USERNAME, String PASSWORD) {

        /*Setting the Loggers*/
        Loggers.setLogger(DatabaseSampleData.class.getName());

        /*Getting the database name*/
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

        /*Calling the Create Table method to add Sample Table and data*/
        createTable();
    }

    /**
     * Creating Sample Table and Values
     */
    public static void createTable() {

        /*Setting the Loggers*/
        Loggers.setLogger(DatabaseSampleData.class.getName());

        /*Establish connection with database*/
        Connection table_connect = JdbcConnection.getConnection();
        String database_table_1 = Property.getProperty("database_table_1");

        try {
            /*Creating the Column Headers*/
            statement = table_connect.createStatement();
            String create_table = "CREATE TABLE " + database_table_1 + " (" +
                    cell_0 + " INT AUTO_INCREMENT NOT NULL," +
                    cell_1 + " VARCHAR(256)," +
                    cell_2 + " VARCHAR(256)," +
                    cell_3 + " INT," +
                    cell_4 + " VARCHAR(256)," +
                    cell_5 + " VARCHAR(256)," +
                    cell_6 + " VARCHAR(256)," +
                    cell_7 + " VARCHAR(256)," +
                    cell_8 + " VARCHAR(256)," +
                    cell_9 + " VARCHAR(256)," +
                    cell_10 + " VARCHAR(256)," +
                    cell_11 + " VARCHAR(256)," +
                    cell_12 + " VARCHAR(256)," +
                    cell_13 + " INT," +
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
                        cell_1 + "," + cell_2 + "," + cell_3 + "," + cell_4 + "," + cell_5 + "," + cell_6 + "," + cell_7 + "," + cell_8 + "," +
                        cell_9 + "," + cell_10 + "," + cell_11 + "," + cell_12 + "," + cell_13 + ")" + " VALUES " + "(?,?,?,?,?,?,?,?,?,?,?,?,?);");

                /*Setting the Column values by fetching it from Excel*/
                preparedStatement.setString(1, ExcelUtils.getDataMap().get(cell_1));
                preparedStatement.setString(2, ExcelUtils.getDataMap().get(cell_2));
                preparedStatement.setInt(3, Integer.parseInt(ExcelUtils.getDataMap().get(cell_3)));
                preparedStatement.setString(4, ExcelUtils.getDataMap().get(cell_4));
                preparedStatement.setString(5, ExcelUtils.getDataMap().get(cell_5));
                preparedStatement.setString(6, ExcelUtils.getDataMap().get(cell_6));
                preparedStatement.setLong(7, Long.parseLong(ExcelUtils.getDataMap().get(cell_7)));
                preparedStatement.setString(8, ExcelUtils.getDataMap().get(cell_8));
                preparedStatement.setString(9, ExcelUtils.getDataMap().get(cell_9));
                preparedStatement.setString(10, ExcelUtils.getDataMap().get(cell_10));
                preparedStatement.setString(11, ExcelUtils.getDataMap().get(cell_11));
                preparedStatement.setString(12, ExcelUtils.getDataMap().get(cell_12));
                preparedStatement.setInt(13, Integer.parseInt(ExcelUtils.getDataMap().get(cell_13)));

                // Executing the PreparedStatement to enter the table values
                preparedStatement.executeUpdate();
            }
            Loggers.getLogger().info("Sample data is added to the '" + database_table_1 + "' table");

        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }

    /*Update the Table if table exists*/
    public static void updateTable() {

        /*Setting the Loggers*/
        Loggers.setLogger(DatabaseSampleData.class.getName());

        /*Establish connection with database*/
        Connection table_connect = JdbcConnection.getConnection();
        String database_table_1 = Property.getProperty("database_table_1");

        try {
            /*Fetching the Database Metadata and Finding the table in the database*/
            databaseMetaData = table_connect.getMetaData();
            resultSet = databaseMetaData.getTables(null, null, database_table_1, null);

            if (resultSet.next()) {
                for (int row = 1; row <= ExcelUtils.getLastRowNumber(); row++) {

                    /*Fetching each row data*/
                    ExcelUtils.getRowData(row);

                    preparedStatement = table_connect.prepareStatement("UPDATE " + database_table_1 + " SET " +
                            cell_1 + " = ?, " + cell_2 + " = ?, " + cell_3 + " = ?, " + cell_4 + " = ?, " +
                            cell_5 + " = ?, " + cell_6 + " = ?, " + cell_7 + " = ?, " + cell_8 + " = ?, " +
                            cell_9 + " = ?, " + cell_10 + " = ?, " + cell_11 + " = ?, " + cell_12 + " = ?, " +
                            cell_13 + " = ? WHERE id = " + ExcelUtils.getDataMap().get(cell_0) + ";");

                    /*Setting the Column values*/
                    preparedStatement.setString(1, ExcelUtils.getDataMap().get(cell_1));
                    preparedStatement.setString(2, ExcelUtils.getDataMap().get(cell_2));
                    preparedStatement.setInt(3, Integer.parseInt(ExcelUtils.getDataMap().get(cell_3)));
                    preparedStatement.setString(4, ExcelUtils.getDataMap().get(cell_4));
                    preparedStatement.setString(5, ExcelUtils.getDataMap().get(cell_5));
                    preparedStatement.setString(6, ExcelUtils.getDataMap().get(cell_6));
                    preparedStatement.setLong(7, Long.parseLong(ExcelUtils.getDataMap().get(cell_7)));
                    preparedStatement.setString(8, ExcelUtils.getDataMap().get(cell_8));
                    preparedStatement.setString(9, ExcelUtils.getDataMap().get(cell_9));
                    preparedStatement.setString(10, ExcelUtils.getDataMap().get(cell_10));
                    preparedStatement.setString(11, ExcelUtils.getDataMap().get(cell_11));
                    preparedStatement.setString(12, ExcelUtils.getDataMap().get(cell_12));
                    preparedStatement.setInt(13, Integer.parseInt(ExcelUtils.getDataMap().get(cell_13)));

                    /*Executing the PreparedStatement to enter the table values*/
                    preparedStatement.executeUpdate();
                    Loggers.getLogger().info("The '" + database_table_1 + "' data is updated");
                }
            } else {
                /*Creating the entire table and data if table doesn't exist*/
                Loggers.getLogger().warn("Table " + database_table_1 + " doesn't exist\n Creating New table with the name '" + database_table_1 + "'...");
                createTable();
            }
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }
    }
}
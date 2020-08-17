package com.magento.mysql;

import com.magento.loggers.Loggers;
import com.magento.utilities.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseSampleData {
    private static Connection no_connect;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static int count = 0;

    /**
     * Creating Sample Database
     * With Sample Table and Values
     */
    public static void createData(String JDBC_DRIVER, String NO_DATABASE_URL, String USERNAME, String PASSWORD) {

        /*Creating Sample database*/
        try {

            /*Setting new connection without Database name*/
            Class.forName(JDBC_DRIVER);
            no_connect = DriverManager.getConnection(NO_DATABASE_URL, USERNAME, PASSWORD);

            /*Creating Database*/
            statement = no_connect.createStatement();
            String database_name_query = "CREATE DATABASE magento_home;";
            statement.executeUpdate(database_name_query);
            Loggers.getLogger().info("Sample Database Created");

            /*Re-Establishing connection after creating new database*/
            JdbcConnection.establishConnection();
        } catch (Exception e) {
            Loggers.getLogger().error(e.getStackTrace()[0] + "\n" + e.getStackTrace()[1]);
        }

        /*Creating Sample Table and Values*/
        Connection table_connect = JdbcConnection.getConnection();
        String database_table_1 = Property.getProperty("database_table_1");

        try {

            /*Creating the Column Headers*/
            statement = table_connect.createStatement();
            String create_table = "CREATE TABLE " + database_table_1 + " (" +
                    "id INT AUTO_INCREMENT NOT NULL," +
                    "email_id VARCHAR(256)," +
                    "password VARCHAR(256)," +
                    "username VARCHAR(256)," +
                    "mobile_number VARCHAR(256)," +
                    "main_category VARCHAR(256)," +
                    "sub_category_1 VARCHAR(256)," +
                    "sub_category_2 VARCHAR(256)," +
                    "product_name VARCHAR(256)," +
                    "qty INT," +
                    "PRIMARY KEY (id));";

            /*Executing the Statement to create Column Headers*/
            statement.executeUpdate(create_table);
            Loggers.getLogger().info("Table with the name '" + database_table_1 + "' is created");

            /*Creating the Table values*/
            preparedStatement = table_connect.prepareStatement("INSERT INTO " + database_table_1 + " VALUES " + "(?,?,?,?,?,?,?,?,?,?);");
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2, "vinay@codilar.com");
            preparedStatement.setString(3, "asdf@1234");
            preparedStatement.setString(4, "vinayms96");
            preparedStatement.setLong(5, 9945723812l);
            preparedStatement.setString(6, "men");
            preparedStatement.setString(7, "top");
            preparedStatement.setString(8, "jackets");
            preparedStatement.setString(9, "Montana Wind Jacket");
            preparedStatement.setInt(10, 1);

            /*Executing the PreparedStatement to enter the table values*/
            preparedStatement.executeUpdate();
            Loggers.getLogger().info("Sample data is added to the '" + database_table_1 + "' table");

        } catch (Exception e) {
            Loggers.getLogger().error(e.getCause());
        }
    }
}

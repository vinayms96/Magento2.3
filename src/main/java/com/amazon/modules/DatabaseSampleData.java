package com.amazon.modules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseSampleData {
    private static Connection connect;
    private static Statement statement;
    private static int count = 0;

    /**
     * Creating Sample Database
     * With Sample Table and Values
     */
    public static void createData(String JDBC_DRIVER, String NO_DATABASE_URL, String USERNAME, String PASSWORD) {

        // Creating Sample database
        try {

            // Setting new connection without Database name
            Class.forName(JDBC_DRIVER);
            connect = DriverManager.getConnection(NO_DATABASE_URL, USERNAME, PASSWORD);

            // Creating Database
            statement = connect.createStatement();
            String database_name_query = "CREATE DATABASE magento_home;";
            statement.executeUpdate(database_name_query);
            Loggers.getLogger().info("Sample Database Created");

            // Re-Establishing connection after creating new database
            JdbcConnection.establishConnection();
        }catch (Exception e){
            Loggers.getLogger().error(e.getStackTrace()[0]+"\n"+e.getStackTrace()[1]);
        }

        // Creating Sample Table and Values
        try {
//            statement = connect.createStatement();
        }catch (Exception e){
            Loggers.getLogger().error(e.getMessage());
        }
    }
}

package com.magento.utilities;

import com.magento.loggers.Loggers;
import com.magento.mysql.JdbcConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DatabaseUtils {
    private static Connection connect = JdbcConnection.getConnection();
    private static Statement stmt;
    private static ResultSet result;
    private static ResultSetMetaData resultSetMetaData;

    /**
     * Execute the query passed and return the ResultSet
     *
     * @param getQuery
     * @return ResultSet
     */
    public static ResultSet getData(String getQuery) {

        // Setting the Loggers
        Loggers.setLogger(DatabaseUtils.class.getName());

        try {
            // Creating the Statement
            stmt = connect.createStatement();
            String query = getQuery;

            // Executing the query passed
            result = stmt.executeQuery(query);

            // Moving the Position of the Cursor to first row
            result.next();

            // Returning the ResultSet
            return result;

        } catch (Exception e) {
            // Logging any error occurred
            Loggers.getLogger().error(e.getMessage());
        }

        return null;
    }

    /**
     * Getting the ResultSet Metadata for column count
     *
     * @return ResultSetMetaData
     */
    public static ResultSetMetaData getResultSetMetaData() {
        // Setting the Loggers
        Loggers.setLogger(DatabaseUtils.class.getName());

        try {
            // Fetching the MetaData
            resultSetMetaData = result.getMetaData();

            // Returning the resultSetMetaData
            return resultSetMetaData;

        } catch (Exception e) {
            // Logging any error occurred
            Loggers.getLogger().error(e.getStackTrace()[0] + ", " + e.getStackTrace()[1]);
        }

        return null;
    }
}

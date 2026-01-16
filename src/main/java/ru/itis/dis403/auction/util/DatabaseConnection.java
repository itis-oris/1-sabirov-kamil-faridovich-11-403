package ru.itis.dis403.auction.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            InputStream in = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream("db.properties");
            props.load(in);
            connection = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));
            return connection;
        } catch (SQLException | java.io.IOException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static void releaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
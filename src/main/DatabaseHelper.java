package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:Apple_Tech_Last_Database.db"; 

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL); 
            System.out.println("Connected to SQLite database.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return connection;
    }
}

package main;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        
        String supplierTable = "CREATE TABLE IF NOT EXISTS Supplier (" +
                               "supplierID INTEGER PRIMARY KEY, " +
                               "supplierName TEXT, " +
                               "supplierTel TEXT, " +
                               "supplierAddress TEXT)";

       
        String productTable = "CREATE TABLE IF NOT EXISTS Product (" +
                              "productID INTEGER PRIMARY KEY, " +
                              "productName TEXT, " +
                              "productPrice REAL, " +
                              "supplierID INTEGER, " +
                              "category TEXT, " +
                              "FOREIGN KEY (supplierID) REFERENCES Supplier(supplierID))";

       
        String stockTable = "CREATE TABLE IF NOT EXISTS Stock (" +
                            "productID INTEGER PRIMARY KEY, " +
                            "stockAmount INTEGER, " +
                            "FOREIGN KEY (productID) REFERENCES Product(productID))";

     
        String salesTable = "CREATE TABLE IF NOT EXISTS Sales (" +
                            "saleID INTEGER PRIMARY KEY, " +
                            "storeID INTEGER, " +
                            "productID INTEGER, " +
                            "salesAmount INTEGER, " +
                            "listPrice REAL, " +
                            "FOREIGN KEY (productID) REFERENCES Product(productID))";

        
        try (Connection conn = DatabaseHelper.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(supplierTable);
            stmt.execute(productTable);
            stmt.execute(stockTable);
            stmt.execute(salesTable);
            System.out.println("Database tables initialized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

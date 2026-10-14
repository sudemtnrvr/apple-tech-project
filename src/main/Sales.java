package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sales {
    private int storeID;
    private Product product;
    private int salesAmount;
    private double listPrice;

    
    public Sales(int storeID, Product product, int salesAmount, double listPrice) {
        this.storeID = storeID;
        this.product = product;
        this.salesAmount = salesAmount;
        this.listPrice = listPrice;
    }

    
    public void saveToDatabase() {
        String sql = "INSERT INTO Sales (storeID, productID, salesAmount, listPrice) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeID);
            pstmt.setInt(2, product.getProductID());
            pstmt.setInt(3, salesAmount);
            pstmt.setDouble(4, listPrice);
            pstmt.executeUpdate();
            System.out.println("Sale saved to database for product: " + product.getProductName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void recordSale() {
        System.out.println("Sale recorded for product: " + product.getProductName());
    }

   
    public void updateSale(int newAmount) {
        this.salesAmount = newAmount;
        System.out.println("Sales amount updated to: " + salesAmount);
        updateSaleInDatabase();
    }

  
    public void deleteSaleRecord() {
        System.out.println("Sale record deleted for product: " + product.getProductName());
        deleteSaleFromDatabase();
    }

    
    public void getSalesDetails() {
        System.out.println("Store ID: " + storeID + ", Product: " + product.getProductName() + ", Sales Amount: " + salesAmount);
    }

    
    private void updateSaleInDatabase() {
        String sql = "UPDATE Sales SET salesAmount = ? WHERE storeID = ? AND productID = ?";
        
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, salesAmount);
            pstmt.setInt(2, storeID);
            pstmt.setInt(3, product.getProductID());
            pstmt.executeUpdate();
            System.out.println("Sale updated in database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private void deleteSaleFromDatabase() {
        String sql = "DELETE FROM Sales WHERE storeID = ? AND productID = ?";
        
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, storeID);
            pstmt.setInt(2, product.getProductID());
            pstmt.executeUpdate();
            System.out.println("Sale deleted from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Stock {
    private Product product;
    private int stockAmount;

   
    public Stock(Product product, int stockAmount) {
        this.product = product;
        this.setStockAmount(stockAmount);
    }

    
    public void saveToDatabase() {
        String sql = "INSERT INTO Stock (productID, stockAmount) VALUES (?, ?)";

        try (Connection conn = DatabaseHelper.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getProductID());
            pstmt.setInt(2, stockAmount);
            pstmt.executeUpdate();
            System.out.println("Stock saved to database for product: " + product.getProductName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void updateStock(int newStock) {
        this.setStockAmount(newStock + getStockAmount());
        System.out.println("Stock updated to: " + getStockAmount());
    }

  

   
    public String checkStockLevel() {
        return product.getProductDetails() + " stock level: " + getStockAmount();
    }

   
    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }
    
    public void deleteStock() {
        String sql = "DELETE FROM Stock WHERE productID = ?";
        
        try (Connection conn = DatabaseHelper.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, product.getProductID());  
            pstmt.executeUpdate();
            
            System.out.println("Stock deleted for Product ID: " + product.getProductID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

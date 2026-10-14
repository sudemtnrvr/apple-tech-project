package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Supplier {
    private int supplierID;
    private String supplierName;
    private String supplierTel;
    private String supplierAddress;

    public Supplier(int supplierID, String supplierName, String supplierTel, String supplierAddress) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierTel = supplierTel;
        this.supplierAddress = supplierAddress;
    }

  
    public void addSupplier() {
        System.out.println("Supplier added: " + supplierName);
    }

    public void deleteSupplier() {
        System.out.println("Supplier deleted: " + supplierName);
    }

  
    public void getSupplierDetails() {
        System.out.println("Supplier ID: " + supplierID + ", Name: " + supplierName);
    }

 
    public void updateSupplier(String newName, String newTel, String newAddress) {
        this.supplierName = newName;
        this.supplierTel = newTel;
        this.supplierAddress = newAddress;
        System.out.println("Supplier updated: " + supplierName);
    }

    public int getSupplierID() {
        return supplierID;
    }
    
    public String getSupplierName() {
    	return supplierName;
    }

    public void saveToDatabase() {
        String sql = "INSERT INTO Supplier (supplierID, supplierName, supplierTel, supplierAddress) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseHelper.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, supplierID);
            pstmt.setString(2, supplierName);
            pstmt.setString(3, supplierTel);
            pstmt.setString(4, supplierAddress);
            pstmt.executeUpdate();
            System.out.println("Supplier saved to database: " + supplierName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

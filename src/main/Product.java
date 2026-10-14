package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Product implements Persistable{
    private int productID;
    private String productName;
    private double productPrice;
    private Supplier supplier;
    private String category;

    public Product(int productID, String productName, double productPrice, Supplier supplier, String category) {
      
        this.productName = productName;
        this.productPrice = productPrice;
        this.supplier = supplier;
        this.category = category;
    }

    public void saveToDatabase() {
        String sql = "INSERT INTO Product (productName, productPrice, supplierID, category) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName);
            pstmt.setDouble(2, productPrice);
            pstmt.setInt(3, supplier.getSupplierID());
            pstmt.setString(4, category);
            pstmt.executeUpdate();
            System.out.println("Product saved to database: " + productName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

	@Override
	public void updateProduct(String newName, double newPrice) {
		productName = newName;
		productPrice = newPrice;
	}

	@Override
	public void deleteProduct() {
		 System.out.println("Supplier deleted: " + productName);
	}

	public void addProduct() {
		System.out.println("Product added: " + productName);
        saveToDatabase();
	}

	public String getProductDetails() {
		return "Product ID: " + productID + ", Name: " + productName + 
	               ", Price: " + productPrice + ", Category: " + category + 
	               ", Supplier: " + supplier.getSupplierName();
		
	}
}

package main;

import java.sql.Connection;
import java.sql.Statement;

public class Apple_Tech {
    public static void main(String[] args) {
        
        DatabaseInitializer.initialize();

       
        Supplier supplier = new Supplier(1, "Tech Supplies", "123456789", "123 Tech Street");
        supplier.addSupplier();
        supplier.getSupplierDetails();

       
        Product product = new Product(101, "Laptop", 1500.0, supplier, "Electronics");
        product.addProduct();
        product.getProductDetails();

     
        Stock stock = new Stock(product, 50);
        stock.checkStockLevel();
        stock.updateStock(20);  
        stock.updateStock(10); 

        
        Sales sale = new Sales(1, product, 5, 1500.0);
        sale.recordSale();
        sale.getSalesDetails();
        
        
    }
}

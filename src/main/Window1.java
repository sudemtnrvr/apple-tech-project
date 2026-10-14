package main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Window1 {

    private static ArrayList<Supplier> suppliers = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<Stock> stocks = new ArrayList<>();

    public static void main(String[] args) {
       
        System.setProperty("java.awt.headless", "false");
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        JFrame frame = new JFrame("Electronics Store Management");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      
        JPanel panel = new JPanel();

       
        JButton addSupplierButton = new JButton("Add Supplier");
        addSupplierButton.setBounds(0, 0, 600, 53);
        JButton addProductButton = new JButton("Add Product");
        addProductButton.setBounds(0, 53, 600, 53);
        JButton updateStockButton = new JButton("Update Stock");
        updateStockButton.setBounds(0, 106, 600, 53);
        JButton viewStocksButton = new JButton("View Stocks");
        viewStocksButton.setBounds(0, 159, 600, 53);
        JButton deleteStockButton = new JButton("Delete Stock");
        deleteStockButton.setBackground(new Color(148, 17, 0));
        deleteStockButton.setBounds(0, 212, 600, 53);
        panel.setLayout(null);

        panel.add(addSupplierButton);
        panel.add(addProductButton);
        panel.add(updateStockButton);
        panel.add(viewStocksButton);
        panel.add(deleteStockButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

      
        addSupplierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                JTextField idField = new JTextField();
                JTextField nameField = new JTextField();
                JTextField telField = new JTextField();
                JTextField addressField = new JTextField();

                Object[] fields = {
                        "ID:", idField, "Name:", nameField, "Telephone:", telField, "Address:", addressField
                };

                int option = JOptionPane.showConfirmDialog(null, fields, "Add Supplier", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        int id = Integer.parseInt(idField.getText());
                        String name = nameField.getText();
                        String tel = telField.getText();
                        String address = addressField.getText();
                        Supplier supplier = new Supplier(id, name, tel, address);
                        suppliers.add(supplier);
                        supplier.saveToDatabase();
                        JOptionPane.showMessageDialog(null, "Supplier Added: " + name);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid data.");
                    }
                }
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              
                JTextField idField = new JTextField();
                JTextField nameField = new JTextField();
                JTextField priceField = new JTextField();
                JTextField categoryField = new JTextField();
                JTextField stockField = new JTextField();

                Object[] fields = {
                        "ID:", idField, "Name:", nameField, "Price:", priceField, "Category:", categoryField, "Initial Stock:", stockField
                };

                int option = JOptionPane.showConfirmDialog(null, fields, "Add Product", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        int id = Integer.parseInt(idField.getText());
                        String name = nameField.getText();
                        double price = Double.parseDouble(priceField.getText());
                        String category = categoryField.getText();
                        int initialStock = Integer.parseInt(stockField.getText());

                        if (suppliers.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please add a supplier first.");
                            return;
                        }

                        Supplier supplier = suppliers.get(0);
                        Product product = new Product(id, name, price, supplier, category);
                        products.add(product);
                        product.saveToDatabase(); 

                        Stock stock = new Stock(product, initialStock);
                        stocks.add(stock);
                        stock.saveToDatabase(); // 

                        JOptionPane.showMessageDialog(null, "Product Added: " + name);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid data.");
                    }
                }
            }
        });

      
        updateStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stocks.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No products in stock to update.");
                    return;
                }

                String[] productNames = products.stream()
                        .map(p -> p.getProductDetails())
                        .toArray(String[]::new);

                JComboBox<String> productComboBox = new JComboBox<>(productNames);
                JTextField stockField = new JTextField();

                Object[] fields = {
                        "Select Product:", productComboBox,
                        "Stock Change (positive to add, negative to reduce):", stockField
                };

                int option = JOptionPane.showConfirmDialog(null, fields, "Update Stock", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    int selectedIndex = productComboBox.getSelectedIndex();
                    int stockChange = Integer.parseInt(stockField.getText());
                    Stock stock = stocks.get(selectedIndex);
                    stock.updateStock(stockChange);

                    JOptionPane.showMessageDialog(null,
                            "Stock Updated for Product: " + products.get(selectedIndex).getProductDetails() +
                                    ". New Stock: " + stock.getStockAmount());
                }
            }
        });

      
        viewStocksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder stockDetails = new StringBuilder();
                for (Stock stock : stocks) {
                    stockDetails.append(stock.checkStockLevel()).append("\n");
                }

                JTextArea textArea = new JTextArea(stockDetails.toString());
                JScrollPane scrollPane = new JScrollPane(textArea);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                scrollPane.setPreferredSize(new Dimension(500, 300));

                JOptionPane.showMessageDialog(null, scrollPane, "Stock Levels", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        deleteStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stocks.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No products in stock to delete.");
                    return;
                }

                String[] productNames = products.stream().map(p -> p.getProductDetails()).toArray(String[]::new);
                JComboBox<String> productComboBox = new JComboBox<>(productNames);
                
                int option = JOptionPane.showConfirmDialog(null, productComboBox, 
                    "Select Product to Delete Stock", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    int selectedIndex = productComboBox.getSelectedIndex();
                    Stock selectedStock = stocks.get(selectedIndex);  
                   
                    
                    selectedStock.deleteStock(); 
                    JOptionPane.showMessageDialog(null, "Stock deleted for Product: " + products.get(selectedIndex).getProductDetails());
                }
            }
        });

    }
}

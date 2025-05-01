import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.sql.*;

public class ViewProductsPage extends JFrame {
    private JTable productTable;

    public ViewProductsPage() {
        setTitle("View Products");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create buttons for category filtering
        JButton manButton = new JButton("Men");
        JButton womanButton = new JButton("Women");
        JButton backButton = new JButton("Back");

        // Add action listeners to buttons
        manButton.addActionListener(e -> displayProducts("Men"));
        womanButton.addActionListener(e -> displayProducts("Women"));
        backButton.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(manButton);
        buttonPanel.add(womanButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Initialize the product table
        productTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons for adding and removing products
        JButton addProductButton = new JButton("Add Product");
        JButton removeProductButton = new JButton("Remove Product");

        addProductButton.addActionListener(e -> new AddProductDialog(ViewProductsPage.this).setVisible(true));
        removeProductButton.addActionListener(e -> new RemoveProductDialog(ViewProductsPage.this).setVisible(true));

        // Panel for add/remove buttons
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.add(addProductButton);
        actionPanel.add(removeProductButton);
        add(actionPanel, BorderLayout.SOUTH);

        // Default view: Display products for 'Men' category
        displayProducts("Men");
    }

    private void displayProducts(String category) {
        String[] columnNames = {"Product ID", "Name", "Price", "Discount", "Category"};
        Object[][] data = {};

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE category = ?")) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            // Fetch rows
            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                rows.add(new Object[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDouble("discount"),
                        rs.getString("category")
                });
            }

            data = rows.toArray(new Object[0][]);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching products", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Use CustomTableModel
        CustomTableModel model = new CustomTableModel(data, columnNames);

        // Set the model to the table
        productTable.setModel(model);

        // Add a TableModelListener to listen for edits in the table
        productTable.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();

                String productId = tableModel.getValueAt(row, 0).toString();
                String updatedName = tableModel.getValueAt(row, 1).toString();
                double updatedPrice = Double.parseDouble(tableModel.getValueAt(row, 2).toString());
                double updatedDiscount = Double.parseDouble(tableModel.getValueAt(row, 3).toString());
                String updatedCategory = tableModel.getValueAt(row, 4).toString();

                // Update the database
                updateProductInDatabase(productId, updatedName, updatedPrice, updatedDiscount, updatedCategory);
            }
        });
    }

    private void updateProductInDatabase(String productId, String name, double price, double discount, String category) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "");
             PreparedStatement stmt = conn.prepareStatement("UPDATE products SET name = ?, price = ?, discount = ?, category = ? WHERE id = ?")) {

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setDouble(3, discount);
            stmt.setString(4, category);
            stmt.setString(5, productId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully in the database!");
            } else {
                JOptionPane.showMessageDialog(this, "No product found with the given ID", "Update Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating product", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getSelectedProductId() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return productTable.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }
}

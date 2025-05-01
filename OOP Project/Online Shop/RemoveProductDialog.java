import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class RemoveProductDialog extends JDialog {
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> productComboBox;
    private JButton deleteButton;

    private static final String[] CATEGORIES = {"Men", "Women"}; // Example categories, adjust if necessary

    public RemoveProductDialog(JFrame parent) {
        super(parent, "Remove Product", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        categoryComboBox = new JComboBox<>(CATEGORIES);
        productComboBox = new JComboBox<>();
        deleteButton = new JButton("Delete");

        categoryComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                updateProductComboBox(selectedCategory); // Update the product list based on selected category
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productComboBox.getSelectedItem();
                if (selectedProduct != null) {
                    boolean success = deleteProductFromDatabase(selectedProduct);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Product deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error deleting product or product not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setLayout(new GridLayout(4, 2));
        add(new JLabel("Select Category:"));
        add(categoryComboBox);
        add(new JLabel("Select Product:"));
        add(productComboBox);
        add(new JLabel());
        add(deleteButton);

        // Initially update the product list for the default category
        updateProductComboBox((String) categoryComboBox.getSelectedItem());
    }

    // Update the product combo box based on the selected category
    private void updateProductComboBox(String category) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        // Fetch the product names for the selected category from the database
        ArrayList<String> productNames = getProductsByCategory(category);
        for (String productName : productNames) {
            model.addElement(productName);
        }

        productComboBox.setModel(model);
    }

    // Fetch product names from the database based on category
    private ArrayList<String> getProductsByCategory(String category) {
        ArrayList<String> productNames = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT name FROM products WHERE category = ?")) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                productNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productNames;
    }

    // Delete the selected product from the database
    private boolean deleteProductFromDatabase(String productName) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "");
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM products WHERE name = ?")) {

            stmt.setString(1, productName);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountDialog extends JDialog {
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> productComboBox;
    private JTextField discountField;
    private JCheckBox applyAllCheckBox;

    public DiscountDialog(JFrame parent) {
        super(parent, "Apply Discount", true);
        setSize(350, 250);
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(parent);

        JLabel categoryLabel = new JLabel("Select Category:");
        JLabel productLabel = new JLabel("Select Product:");
        JLabel discountLabel = new JLabel("Discount Percentage:");

        categoryComboBox = new JComboBox<>(new String[]{"Men", "Women"});
        productComboBox = new JComboBox<>();
        discountField = new JTextField(15);
        applyAllCheckBox = new JCheckBox("Apply to all products");

        categoryComboBox.addActionListener(e -> populateProductComboBox((String) categoryComboBox.getSelectedItem()));

        populateProductComboBox("Men"); // Default to Men category

        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double discount = Double.parseDouble(discountField.getText());
                    applyDiscount(discount);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(parent, "Please enter a valid discount percentage!", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        add(categoryLabel);
        add(categoryComboBox);
        add(productLabel);
        add(productComboBox);
        add(discountLabel);
        add(discountField);
        add(new JLabel());  // Empty space
        add(applyAllCheckBox);
        add(applyButton);
        add(cancelButton);
    }

    private void populateProductComboBox(String category) {
        productComboBox.removeAllItems();
        System.out.println("Fetching products for category: " + category); // Debugging output

        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "SELECT name FROM products WHERE category = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, category);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String productName = rs.getString("name");
                        productComboBox.addItem(productName);
                        System.out.println("Product added: " + productName);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyDiscount(double discount) {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query;
            if (applyAllCheckBox.isSelected()) {
                query = "UPDATE products SET discount = ?";
            } else {
                query = "UPDATE products SET discount = ? WHERE name = ?";
            }

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, discount);
                if (!applyAllCheckBox.isSelected()) {
                    stmt.setString(2, (String) productComboBox.getSelectedItem());
                }
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Discount applied successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No products updated!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
}

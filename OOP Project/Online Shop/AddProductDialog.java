import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddProductDialog extends JDialog {
    private JTextField nameField;
    private JTextField priceField;
    private JComboBox<String> categoryComboBox;

    public AddProductDialog(JFrame parent) {
        super(parent, "Add Product", true);
        setSize(300, 200);
        setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Product Name:");
        JLabel priceLabel = new JLabel("Product Price:");
        JLabel categoryLabel = new JLabel("Category:");

        nameField = new JTextField(15);
        priceField = new JTextField(15);
        categoryComboBox = new JComboBox<>(new String[]{"Men", "Women"});

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String category = (String) categoryComboBox.getSelectedItem();

                addProductToDatabase(name, price, category);
                JOptionPane.showMessageDialog(parent, "Product added successfully");
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(nameLabel);
        add(nameField);
        add(priceLabel);
        add(priceField);
        add(categoryLabel);
        add(categoryComboBox);
        add(addButton);
        add(cancelButton);
    }

    private void addProductToDatabase(String name, double price, String category) {
        String url = "jdbc:mysql://localhost:3306/online_shop"; // Update if needed
        String user = "root"; // Default user in XAMPP
        String password = ""; // No password by default

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO products (name, price, category) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setDouble(2, price);
                stmt.setString(3, category);
                stmt.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding product to database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

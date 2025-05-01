import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminPanel extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;

    public AdminPanel() {
        setTitle("Admin Panel - View All Products");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table column names
        String[] columnNames = {"id", "name", "price",  "discount","category",};

        // Create table model and set it to the table
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);

        // Add table inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load products from database
        loadProductsFromDatabase();
    }

    private void loadProductsFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/online_shop";
        String user = "root";
        String password = "";

        String query = "SELECT id, name, price, category, discount, stock FROM products";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Clear existing data
            tableModel.setRowCount(0);

            // Add rows to the table
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double discount = rs.getDouble("discount");
                String category = rs.getString("category");

                // Add data to the table
                tableModel.addRow(new Object[]{id, name, price, category, discount});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error loading products!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanel().setVisible(true));
    }
}

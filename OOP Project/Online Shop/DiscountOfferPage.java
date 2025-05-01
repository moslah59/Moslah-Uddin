import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountOfferPage extends JFrame {
    public DiscountOfferPage() {
        setTitle("Discount Offers");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Product Name", "Discounted Price"};
        List<Product> discountedProducts = getDiscountedProducts();

        Object[][] data = new Object[discountedProducts.size()][2];
        int index = 0;
        for (Product product : discountedProducts) {
            data[index][0] = product.getName();
            data[index][1] = product.getDiscountedPrice();
            index++;
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerDashboard().setVisible(true);  // Assuming CustomerDashboard exists
                dispose();
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    public List<Product> getDiscountedProducts() {
        List<Product> discountedProducts = new ArrayList<>();
        String query = "SELECT name, price * (1 - discount / 100) AS discounted_price FROM products WHERE discount > 0";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String productName = rs.getString("name");
                double discountedPrice = rs.getDouble("discounted_price");
                String category = "Unknown"; // Default category since it's not retrieved from DB

                // Fix: Pass proper parameters
                double price = 0;
                discountedProducts.add(new Product(-1, productName, price, discountedPrice, 0, category, 0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return discountedProducts;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DiscountOfferPage().setVisible(true);
            }
        });
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManProductsPageCustomer extends JFrame {

    public ManProductsPageCustomer() {
        setTitle("Men's Products");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JTable to display the products
        String[] columns = {"ID", "Name", "Price", "Discount", "Discounted Price", "Add to Cart"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch products for the Men category from the database
        List<Product> productList = ProductDatabase.getProductsByCategory("Men");

        // Add rows to the table
        for (Product product : productList) {
            Object[] row = {
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDiscount(),
                    product.getDiscountedPrice(),
                    "Add to Cart" // Button column
            };
            model.addRow(row);
        }

        // Add button functionality to JTable
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), table, productList));

        // **Back Button to View Products Page**
        JButton backButton = new JButton("Back to Categories");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current page
                new ViewProductsPageCustomer().setVisible(true); // Go back to product categories
            }
        });

        // Add back button below the table
        JPanel panel = new JPanel();
        panel.add(backButton);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }
}

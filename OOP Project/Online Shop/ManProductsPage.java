import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class ManProductsPage extends JFrame {
    private JTable table;
    private List<Product> manProducts;

    public ManProductsPage() {
        setTitle("Man Products");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Product ID", "Product Name", "Price", "Add to Cart"};
        manProducts = Product.getProductsByCategory("man"); // Assuming this method gets products from the database

        Object[][] data = new Object[manProducts.size()][4];
        int index = 0;
        for (Product product : manProducts) {
            data[index][0] = product.getId(); // Assuming product has an ID
            data[index][1] = product.getName();
            data[index][2] = product.getDiscount() > 0 ? product.getDiscountedPrice() : product.getPrice();
            data[index][3] = "Add to Cart"; // Text to display on the button
            index++;
        }

        table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 3; // Make the "Add to Cart" column clickable
            }
        };

        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Add ButtonRenderer to render "Add to Cart" as a button
        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());

        // Use ButtonEditor to handle button clicks
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), table, manProducts));

        JScrollPane scrollPane = new JScrollPane(table);

        // Back button to navigate back to the customer dashboard
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new CustomerDashboard().setVisible(true); // Assuming you have CustomerDashboard class
            dispose();
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}

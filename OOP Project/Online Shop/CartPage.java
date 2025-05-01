import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CartPage extends JFrame {
    private double totalPrice;
    private int userId;
    private JTable table;

    public CartPage(int userId) {
        this.userId = userId;
        setTitle("Shopping Cart");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Product Name", "Price", "Quantity", "Stock"};

        // Fetch cart products from MySQL
        List<Product> cartProducts = ProductDatabase.getCartProducts(userId);

        if (cartProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!");
            return;
        }

        Object[][] data = new Object[cartProducts.size()][4];
        totalPrice = 0;
        int index = 0;
        for (Product product : cartProducts) {
            data[index][0] = product.getName();
            data[index][1] = product.getDiscount() > 0 ? product.getDiscountedPrice() : product.getPrice();
            data[index][2] = product.getQuantity();  // Use quantity from cart
            data[index][3] = product.getStock();
            totalPrice += product.getDiscount() > 0 ? product.getDiscountedPrice() : product.getPrice(); // Add correct price
            index++;
        }

        table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };

        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(table);

        JButton orderButton = new JButton("Place Order");
        JButton backButton = new JButton("Back");

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Total Price: " + totalPrice);
                showPaymentPage();
                dispose();
            }
        });


        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerDashboard().setVisible(true); // Navigate back to the dashboard
                dispose();
            }
        });

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BorderLayout());
        JLabel totalPriceLabel = new JLabel("Total Price: " + totalPrice);
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalPriceLabel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.NORTH);
        add(orderButton, BorderLayout.SOUTH);
        add(backButton, BorderLayout.WEST);
    }

    public CartPage() {

    }

    private void showPaymentPage() {
        List<Product> cartProducts = ProductDatabase.getCartProducts(userId);

        double totalAmount = 0;
        for (Product product : cartProducts) {
            totalAmount += product.getDiscount() > 0 ? product.getDiscountedPrice() : product.getPrice();
        }

        JFrame paymentFrame = new JFrame("Payment");
        paymentFrame.setSize(300, 200);
        paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paymentFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(String.valueOf(totalAmount));
        amountField.setEditable(false);

        JButton confirmButton = new JButton("Confirm Payment");
        double finalTotalAmount = totalAmount;
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String phone = phoneField.getText();
                if (phone.length() != 11) {
                    JOptionPane.showMessageDialog(null, "Phone number must be 11 digits.");
                    return;
                }

                int orderId = 0;
                String status = "Pending";
                Order newOrder = new Order(orderId, nameField.getText(), phoneField.getText(), finalTotalAmount, status, cartProducts);
                newOrder.saveToDatabase();  // Save the order and products in the database

                ProductDatabase.clearCart(userId); // Clears the cart for the specific user

                JOptionPane.showMessageDialog(null, "Payment successful!");
                paymentFrame.dispose();
                new CustomerDashboard().setVisible(true); // Navigate back to the dashboard
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(new JLabel());
        panel.add(confirmButton);

        paymentFrame.add(panel);
        paymentFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CartPage(1).setVisible(true);
            }
        });
    }
}

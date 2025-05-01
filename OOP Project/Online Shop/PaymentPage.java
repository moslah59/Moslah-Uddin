import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PaymentPage extends JFrame {
    private JTextField phoneNumberField;
    private int userId;
    private double totalPrice;

    public PaymentPage(int userId, double totalPrice) {
        this.userId = userId;
        this.totalPrice = totalPrice;

        setTitle("Payment");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel label = new JLabel("Enter Mobile Number for Payment:");
        phoneNumberField = new JTextField();

        JButton payButton = new JButton("Pay Now");
        payButton.addActionListener(e -> processPayment());

        panel.add(label);
        panel.add(phoneNumberField);
        panel.add(payButton);
        add(panel);

        setVisible(true);
    }

    private void processPayment() {
        String phoneNumber = phoneNumberField.getText().trim();
        if (phoneNumber.isEmpty() || !phoneNumber.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 11-digit phone number!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "");
             PreparedStatement psOrder = conn.prepareStatement(
                     "INSERT INTO orders (user_id, total_price, payment_status) VALUES (?, ?, 'Pending')",
                     Statement.RETURN_GENERATED_KEYS);
             PreparedStatement deleteCart = conn.prepareStatement("DELETE FROM cart_item WHERE user_id = ?")) {

            // Insert new order
            psOrder.setInt(1, userId);
            psOrder.setDouble(2, totalPrice);
            psOrder.executeUpdate();

            // Get generated order ID
            ResultSet generatedKeys = psOrder.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            // Clear user's cart after placing the order
            deleteCart.setInt(1, userId);
            deleteCart.executeUpdate();

            // Mark order as paid
            if (orderId != -1) {
                completePayment(orderId);
            }

            JOptionPane.showMessageDialog(this, "Payment Successful! Order Placed.");
            sendConfirmationSMS(phoneNumber);
            dispose();
            new CustomerDashboard().setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Payment failed. Try again.");
        }
    }

    private void completePayment(int orderId) {
        String updatePaymentQuery = "UPDATE orders SET payment_status = 'Paid' WHERE order_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "");
             PreparedStatement ps = conn.prepareStatement(updatePaymentQuery)) {

            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendConfirmationSMS(String phoneNumber) {
        // Placeholder for SMS sending logic
        System.out.println("SMS sent to " + phoneNumber + ": Your order has been successfully placed. Thank you for shopping with us!");
    }
}

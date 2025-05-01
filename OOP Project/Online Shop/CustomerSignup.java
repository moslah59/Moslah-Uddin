import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerSignup extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public CustomerSignup() {
        setTitle("Customer Signup");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton signupButton = new JButton("Signup");

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                } else {
                    if (registerUser(username, password)) {
                        JOptionPane.showMessageDialog(null, "Signup successful");
                        new CustomerPage().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Username already exists!");
                    }
                }
            }
        });

        setLayout(new GridLayout(4, 2));
        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(new JLabel()); // Empty cell
        add(signupButton);
    }

    private boolean registerUser(String username, String password) {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) return false;

            // Check if username already exists
            String checkQuery = "SELECT * FROM customers WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return false; // Username already exists
            }

            // Insert new user
            String insertQuery = "INSERT INTO customers (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

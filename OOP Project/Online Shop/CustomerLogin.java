import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public CustomerLogin() {
        setTitle("Customer Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (validateUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    new CustomerDashboard().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials");
                    new CustomerPage().setVisible(true);
                    dispose();
                }
            }
        });

        setLayout(new GridLayout(3, 2));
        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(new JLabel()); // Empty cell
        add(loginButton);
    }

    private boolean validateUser(String username, String password) {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) return false;

            String query = "SELECT * FROM customers WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // If a row is returned, the user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

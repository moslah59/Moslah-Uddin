import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLogin() {
        setTitle("Admin Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:", JLabel.CENTER);
        userLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        JLabel passLabel = new JLabel("Password:", JLabel.CENTER);
        passLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticateAdmin(username, password)) {
                    new AdminDashboard().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
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

    private boolean authenticateAdmin(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/online_shop"; // Update if needed
        String user = "root"; // Default XAMPP user
        String pass = ""; // Default XAMPP password (empty)

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Returns true if admin exists
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPage extends JFrame {
    public CustomerPage() {
        setTitle("Customer Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerLogin().setVisible(true);
                dispose();
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerSignup().setVisible(true);
                dispose();
            }
        });

        setLayout(new GridLayout(3, 1));
        add(new JLabel("Customer Page", SwingConstants.CENTER));
        add(loginButton);
        add(signupButton);
    }
}

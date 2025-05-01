import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {
    public WelcomePage() {
        setTitle("Welcome to Our Online Shop");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton adminButton = new JButton("Admin");
        JButton customerButton = new JButton("Customer");

        // Admin button action
        adminButton.addActionListener(e -> {
            new AdminLogin().setVisible(true);  // Open Admin Login Page
            dispose(); // Close WelcomePage
        });

        // Customer button action
        customerButton.addActionListener(e -> {
            new CustomerPage().setVisible(true);  // Open Customer Page
            dispose(); // Close WelcomePage
        });

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(adminButton);
        buttonPanel.add(customerButton);

        // Styled Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to Our Online Shop", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(72, 61, 139));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Adjust button sizes and fonts
        adminButton.setPreferredSize(new Dimension(160, 40));
        adminButton.setFont(new Font("Arial", Font.BOLD, 25));

        customerButton.setPreferredSize(new Dimension(160, 40));
        customerButton.setFont(new Font("Arial", Font.BOLD, 25));

        // Developer Section
        JPanel developerPanel = new JPanel();
        developerPanel.setLayout(new GridLayout(6, 1));

        JLabel developedByLabel = new JLabel("Developed by:", SwingConstants.CENTER);
        developedByLabel.setFont(new Font("Serif", Font.BOLD, 20));
        developedByLabel.setForeground(Color.BLUE);

        JLabel dev1 = new JLabel("Moslah Uddin", SwingConstants.CENTER);
        JLabel dev2 = new JLabel("Sadia Afrin Tanni", SwingConstants.CENTER);
        JLabel dev3 = new JLabel("Mahfuza Binta Islam Medha", SwingConstants.CENTER);
        JLabel dev4 = new JLabel("Mirajul Islam Onik", SwingConstants.CENTER);
        JLabel dev5 = new JLabel("Aronnadas Aronna", SwingConstants.CENTER);

        Font devFont = new Font("Serif", Font.ITALIC, 20);
        Color devColor = new Color(0, 128, 0); // Dark green color

        dev1.setFont(devFont);
        dev1.setForeground(devColor);
        dev2.setFont(devFont);
        dev2.setForeground(devColor);
        dev3.setFont(devFont);
        dev3.setForeground(devColor);
        dev4.setFont(devFont);
        dev4.setForeground(devColor);
        dev5.setFont(devFont);
        dev5.setForeground(devColor);

        developerPanel.add(developedByLabel);
        developerPanel.add(dev1);
        developerPanel.add(dev2);
        developerPanel.add(dev3);
        developerPanel.add(dev4);
        developerPanel.add(dev5);

        add(developerPanel, BorderLayout.SOUTH);
    }

    // Make sure WelcomePage is the first window to open
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage().setVisible(true));
    }
}

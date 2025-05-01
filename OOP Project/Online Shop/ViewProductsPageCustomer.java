import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewProductsPageCustomer extends JFrame {

    public ViewProductsPageCustomer() {
        setTitle("View Products");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton manButton = new JButton("Men");
        JButton womanButton = new JButton("Women");
        JButton backButton = new JButton("Back");

        manButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManProductsPageCustomer().setVisible(true);
                dispose();
            }
        });

        womanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WomanProductsPageCustomer().setVisible(true);
                dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerDashboard().setVisible(true);
                dispose();
            }
        });

        setLayout(new GridLayout(3, 1));
        add(manButton);
        add(womanButton);
        add(backButton);
    }

    public static void main(String[] args) {
        new ViewProductsPageCustomer().setVisible(true);
    }
}

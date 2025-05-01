import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDashboard extends JFrame {
    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton mainMenuButton = new JButton("Main Menu");
        JButton viewProductsButton = new JButton("View All Products");
        JButton cartButton = new JButton("Cart");
        JButton discountOfferButton = new JButton("Discount Offer");

        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WelcomePage().setVisible(true);
                dispose();
            }
        });

        viewProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewProductsPageCustomer().setVisible(true);
                dispose();
            }
        });

        cartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CartPage().setVisible(true);
                dispose();
            }
        });

        discountOfferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DiscountOfferPage().setVisible(true);
                dispose();
            }
        });

        setLayout(new GridLayout(5, 1));
        add(mainMenuButton);
        add(viewProductsButton);
        add(cartButton);
        add(discountOfferButton);
    }
}

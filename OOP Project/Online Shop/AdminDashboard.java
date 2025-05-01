import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton mainMenuButton = new JButton("Main Menu");
        JButton viewProductsButton = new JButton("View All Products");
        JButton addProductButton = new JButton("Add Product");
        JButton removeProductButton = new JButton("Remove Product");
        JButton discountButton = new JButton("Discount");
        JButton viewOrdersButton = new JButton("View Orders");

        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WelcomePage().setVisible(true);
                dispose();
            }
        });

        viewProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewProductsPage().setVisible(true);
                dispose();
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddProductDialog(AdminDashboard.this).setVisible(true);
            }
        });

        removeProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RemoveProductDialog(AdminDashboard.this).setVisible(true);
            }
        });

        discountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Discount button clicked!"); // Debugging output
                SwingUtilities.invokeLater(() -> {
                    DiscountDialog discountDialog = new DiscountDialog(AdminDashboard.this);
                    discountDialog.setVisible(true);
                });
            }
        });



        viewOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewOrdersPage().setVisible(true);
                dispose();
            }
        });

        setLayout(new GridLayout(6, 1));
        add(mainMenuButton);
        add(viewProductsButton);
        add(addProductButton);
        add(removeProductButton);
        add(discountButton);
        add(viewOrdersButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}

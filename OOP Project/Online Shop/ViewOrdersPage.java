import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewOrdersPage extends JFrame {

    public ViewOrdersPage() {
        setTitle("View Orders");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Order ID", "Customer Name", "Phone", "Amount", "Status"};
        List<Order> orders = OrderDatabase.getOrders();

        Object[][] data = new Object[orders.size()][5];
        int index = 0;
        for (Order order : orders) {
            data[index][0] = order.getOrderId();
            data[index][1] = order.getCustomerName();
            data[index][2] = order.getCustomerPhone();
            data[index][3] = order.getTotalAmount();
            data[index][4] = order.getStatus();
            index++;
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminDashboard().setVisible(true);
                dispose();
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewOrdersPage().setVisible(true); // Open orders view page
            }
        });
    }
}

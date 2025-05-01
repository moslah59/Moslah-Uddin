import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JTable table; // Store the JTable instance
    private List<Product> products; // No longer needed for DB integration

    public ButtonEditor(JCheckBox checkBox, JTable table, List<Product> products) {
        super(checkBox);
        this.button = new JButton();
        this.table = table;
        this.products = products; // Will be replaced with MySQL operations
        this.button.setOpaque(true);
        this.button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String productId = table.getValueAt(row, 0).toString(); // Assuming first column is product ID
            String productName = table.getValueAt(row, 1).toString();
            addToCart(productId, productName);
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    private void addToCart(String productId, String productName) {
        String url = "jdbc:mysql://localhost:3306/online_shop"; // Update if needed
        String user = "root"; // Default user in XAMPP
        String password = ""; // No password by default

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE products SET in_cart = TRUE WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(productId));
                stmt.executeUpdate();
            }
            JOptionPane.showMessageDialog(button, productName + " added to cart!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(button, "Error adding product to cart!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

import java.sql.*;
import java.util.List;

public class Order {
    private String customerName;
    private String phoneNumber;
    private double amount;
    private List<Product> products;
    private int orderId;
    private String status;

    // Constructor for creating an order
    public Order(int orderId, String customerName, String phoneNumber, double amount, String status, List<Product> cartProducts) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.products = products;
    }

    // Constructor for reading an order from the database
    public Order(int orderId, String customerName, String customerPhone, double totalPrice, String status, String productsText) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.phoneNumber = customerPhone;
        this.amount = totalPrice;
        this.status = status;
        // Here, we could deserialize the product list from the `productsText` string (e.g., in JSON format)
        // For simplicity, assume it's just a comma-separated list of product names and quantities
    }

    // Save the order into the database
    public void saveToDatabase() {
        String insertOrderQuery = "INSERT INTO orders (customer_name, phone_number, total_amount, status, products) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "");
             PreparedStatement stmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {

            // Convert product list to a simple string (e.g., "productID1:quantity1,productID2:quantity2")
            StringBuilder productsText = new StringBuilder();
            for (Product product : products) {
                if (productsText.length() > 0) {
                    productsText.append(",");
                }
                productsText.append(product.getId()).append(":1"); // Assuming quantity 1 for simplicity
            }

            stmt.setString(1, customerName);
            stmt.setString(2, phoneNumber);
            stmt.setDouble(3, amount);
            stmt.setString(4, "pending");  // Default status
            stmt.setString(5, productsText.toString());
            stmt.executeUpdate();

            // Retrieve the generated order ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.orderId = generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters for the order details
    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setOrderId(int anInt) {

    }

    public double getTotalPrice() {
        return 0;
    }

    public void setProducts(List<Product> products) {
    }

    public Object getCustomerPhone() {
        return null;
    }

    public Object getTotalAmount() {
        return null;
    }
}

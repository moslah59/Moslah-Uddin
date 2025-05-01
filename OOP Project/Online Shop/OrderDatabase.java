import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/online_shop"; // Update the database name here
    private static final String USER = "root"; // Default MySQL username
    private static final String PASSWORD = ""; // Default MySQL password (if set)

    // Add a new order to the database
    public static void addOrder(Order order) {
        String query = "INSERT INTO orders (customer_name, customer_phone, total_price) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, order.getCustomerName());
            preparedStatement.setString(2, order.getPhoneNumber());
            preparedStatement.setDouble(3, order.getTotalPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all orders from the database
    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int orderId = resultSet.getInt("id");
                String customerName = resultSet.getString("customer_name");
                String customerPhone = resultSet.getString("customer_phone");
                double totalPrice = resultSet.getDouble("total_price");
                String status = resultSet.getString("status");

                // Fetch products for the order
                List<Product> products = getProductsForOrder(orderId);

                List<Product> cartProducts = List.of();
                Order order = new Order(orderId, customerName, customerPhone, totalPrice, status, cartProducts);
                order.setProducts(products);  // Set the products for the order
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    static List<Product> getProductsForOrder(int orderId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.price, p.discount, p.category, p.stock FROM products p "
                + "JOIN order_products op ON p.id = op.product_id WHERE op.order_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                double discount = resultSet.getDouble("discount");
                String category = resultSet.getString("category");
                int stock = resultSet.getInt("stock");

                products.add(new Product(id, name, price, price, discount, category, stock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static List<Order> getOrders() {
        return List.of();
    }
}

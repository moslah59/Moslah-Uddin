import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/online_shop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Method to get products by category
    public static List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category = ?";  // Corrected table name

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");  // Corrected column name
                String name = rs.getString("name");  // Corrected column name
                double price = rs.getDouble("price");
                double discount = rs.getDouble("discount");
                double discountedPrice = price - (price * discount / 100);
                int stock = rs.getInt("stock");
                boolean inCart = rs.getBoolean("in_cart");

                productList.add(new Product(id, name, price, discount, category, stock, false));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
    public static void addToCart(int userId, int productId, double finalPrice) {
        String query = "INSERT INTO cart_item (cart_id, user_id, product_id, final_price) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, userId);
            pst.setInt(2, productId);
            pst.setDouble(3, finalPrice);  // Ensure correct final price is passed here
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to get products in the user's cart
    public static List<Product> getCartProducts(int userId) {
        List<Product> cartList = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.price, p.discount, p.category, p.stock, c.final_price, c.quantity " +
                "FROM products p " +
                "JOIN cart_item c ON p.id = c.product_id " +
                "WHERE c.user_id = ?";  // Ensure correct column references

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double discount = rs.getDouble("discount");
                String category = rs.getString("category");
                int stock = rs.getInt("stock");
                double finalPrice = rs.getDouble("final_price");  // Ensure correct final_price from cart_item table
                int quantity = rs.getInt("quantity");

                cartList.add(new Product(id, name, price, discount, category, stock, finalPrice, quantity)); // Ensure proper constructor
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartList;
    }


    // Method to clear items from a user's cart
    public static void clearCart(int userId) {
        String query = "DELETE FROM cart_item WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add an order to the database
    public static void addOrder(Order order) {
        String query = "INSERT INTO orders (customer_name, customer_phone, total_amount, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, order.getCustomerName());
            stmt.setString(2, order.getPhoneNumber());
            stmt.setDouble(3, order.getAmount());
            stmt.setString(4, "pending");

            stmt.executeUpdate();

            // Retrieve generated order ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setOrderId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

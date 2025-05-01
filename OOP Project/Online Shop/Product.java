import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private double price;
    private double discount;
    private String category;
    private int stock;
    private boolean in_cart; // Indicates if the product is in the cart

    // Constructor with all fields
    public Product(int id, String name, double price, double discount, String category, int stock, boolean in_cart) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.category = category;
        this.stock = stock;
        this.in_cart = in_cart;
    }

    // Constructor without in_cart field (default false)
    public Product(int id, String name, double v, double price, double discount, String category, int stock) {
        this(id, name, price, discount, category, stock, false);
    }

    public Product(int id, String name, double price, double discount, String category, int stock, double finalPrice, int quantity) {

    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isInCart() {
        return in_cart;
    }

    public void setInCart(boolean in_cart) {
        this.in_cart = in_cart;
    }

    // Calculate discounted price
    public double getDiscountedPrice() {
        return price * (1 - discount / 100);
    }

    // Fetch products by category from the database
    public static List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category = ?"; // Table name corrected

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_shop", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double discount = rs.getDouble("discount");
                int stock = rs.getInt("stock");
                boolean in_cart = rs.getBoolean("in_cart");

                productList.add(new Product(id, name, price, discount, category, stock, in_cart));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public double getQuantity() {
        return 0;
    }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connect() {
        String url = "jdbc:mysql://localhost:3306/online_shop"; // ✅ Correct DB name
        String username = "root"; // ✅ Default MySQL user
        String password = ""; // ✅ No password for XAMPP

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Database connected successfully!");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}

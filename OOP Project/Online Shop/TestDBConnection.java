import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/online_shop";
        String user = "root"; // Your MySQL username
        String password = ""; // Your MySQL password (leave empty if no password)

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("✅ Database Connected Successfully!");
            } else {
                System.out.println("❌ Database Connection Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

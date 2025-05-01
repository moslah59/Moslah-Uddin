import javax.swing.*;

public class OnlineShop {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WelcomePage().setVisible(true);  // Launch the WelcomePage
            }
        });
    }
}

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import login.login;

public class main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Gagal set Look and Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            login form = new login();
            form.setVisible(true);
        });
    }
}
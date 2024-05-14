import javax.swing.*;

public class PharmacyWindow extends JFrame {
    public PharmacyWindow() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and feel
        } catch (
                ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e
        ) {
            e.printStackTrace();
        }
        setContentPane(mainPanel);
        setTitle("PHARMACY APP!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JPanel mainPanel;
    private JButton addDrugButton;
    private JButton removeDrugButton;
    private JButton placeAnOrderButton;
    private JButton viewSalesButton;

    public static void main(String[] args) {
        new PharmacyWindow();
    }
}

import javax.swing.*;

public class PharmacyWindow extends JFrame {
    private Pharmacy pharmacy;
    private JPanel mainPanel;
    private JButton addDrugButton;
    private JButton removeDrugButton;
    private JButton placeAnOrderButton;
    private JButton viewSalesButton;

    public PharmacyWindow() {
        pharmacy = new Pharmacy(100);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and Feel
        } catch (
                ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e
        ) {
            e.printStackTrace();
        }
        setContentPane(mainPanel);
        setTitle("Pharmacy App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 360);
        setLocationRelativeTo(null);
        setVisible(true);


        addDrugButton.addActionListener(e -> {
            AddDrugDialog dialog = new AddDrugDialog(this, true, pharmacy);
            dialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        new PharmacyWindow();
    }
}

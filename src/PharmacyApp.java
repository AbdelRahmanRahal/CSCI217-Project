/*
    Team Members:
    AbdelRahman Rahal — 221001443
    Mahmoud Mohamed — 221001313
    Nour Elsharkawy — 221001458
 */
import javax.swing.*;
import java.util.List;

public class PharmacyApp extends JFrame {
    private Pharmacy pharmacy;
    private JPanel mainPanel;
    private JButton addDrugButton;
    private JButton removeDrugButton;
    private JButton placeAnOrderButton;
    private JButton viewSalesButton;


    public PharmacyApp() {
        pharmacy = new Pharmacy(100);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and Feel
        } catch (
                ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e
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

        removeDrugButton.addActionListener(e -> {
            RemoveDrugDialog dialog = new RemoveDrugDialog(this, true, pharmacy);
            dialog.setVisible(true);
        });

        placeAnOrderButton.addActionListener(e -> {
            PlaceOrderDialog dialog = new PlaceOrderDialog(this, true, pharmacy);
            dialog.setVisible(true);
        });

        viewSalesButton.addActionListener(e -> {
            List<Transaction> transactions = pharmacy.getTransactions();
            StringBuilder transactionHistory = new StringBuilder("Sales history:\n\n");
            double totalSales = 0.0;

            for (Transaction transaction : transactions) {
                transactionHistory.append("Drug Name: ").append(transaction.getDrugName())
                                .append(", Drug ID: ").append(transaction.getDrugId())
                                .append(", Quantity Sold: ").append(transaction.getQuantitySold())
                                .append(", Total Price: ").append(transaction.getTotalPrice())
                                .append(" EGP\n");
                totalSales += transaction.getTotalPrice();
            }

            transactionHistory.append("\nTotal Sales: ").append(totalSales).append(" EGP");
            JOptionPane.showMessageDialog(this, transactionHistory.toString(), "Sales Information", JOptionPane.INFORMATION_MESSAGE);
        });
    }
    public static void main(String[] args) {
        new PharmacyApp();
    }
}

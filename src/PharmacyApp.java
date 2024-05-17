/*
    Team Members:
    AbdelRahman Rahal — 221001443
    Mahmoud Mohamed — 221001313
    Nour Elsharkawy — 221001458
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PharmacyApp extends JFrame {
    private Pharmacy pharmacy;
    private JPanel mainPanel;
    private JButton addDrugButton;
    private JButton removeDrugButton;
    private JButton placeAnOrderButton;
    private JButton viewSalesButton;


    public PharmacyApp(int capacity) {
        pharmacy = new Pharmacy(100);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and Feel
        } catch (
                ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e
        ) {
            e.printStackTrace();
        }

        try {
            setIconImage(ImageIO.read(new File("src/icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        Scanner keyboard = new Scanner(System.in);
        int capacity;
        System.out.print("Enter the capacity of the pharmacy: ");
        while (true) {
            try {
                capacity = keyboard.nextInt();
                capacity = Math.abs(capacity);
                break;
            } catch (Exception e) {
                System.out.print("Invalid value, please try again.");
            }
        }
        new PharmacyApp(capacity);
    }
}

 import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class PharmacyApp extends JFrame {
    private Pharmacy pharmacy;
    private JTextField drugIdField;
    private JTextField drugNameField;
    private JTextField drugPriceField;
    private JComboBox<String> drugCategoryComboBox;
    private JTextField drugQuantityField;
    private JButton addButton;
    private JButton removeButton;
    private JButton placeOrderButton;
    private JButton viewSalesButton;
    private JTextArea salesReportArea;

    public PharmacyApp(int capacity) {
        pharmacy = new Pharmacy(100); // Initialize with a capacity of 100 drugs

        // Set up the UI components
        drugIdField = new JTextField(20);
        drugNameField = new JTextField(20);
        drugPriceField = new JTextField(20);
        drugCategoryComboBox = new JComboBox<>(new String[]{"Cosmetics", "Prescription Drugs", "Other"});
        drugQuantityField = new JTextField(20);
        addButton = new JButton("Add Drug");
        removeButton = new JButton("Remove Drug");
        placeOrderButton = new JButton("Place Order");
        viewSalesButton = new JButton("View Sales");
        salesReportArea = new JTextArea(10, 30);

        // Add components to the frame
        setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new GridLayout(5, 2));
        northPanel.add(new JLabel("Drug ID:"));
        northPanel.add(drugIdField);
        northPanel.add(new JLabel("Drug Name:"));
        northPanel.add(drugNameField);
        northPanel.add(new JLabel("Price:"));
        northPanel.add(drugPriceField);
        northPanel.add(new JLabel("Category:"));
        northPanel.add(drugCategoryComboBox);
        northPanel.add(new JLabel("Quantity:"));
        northPanel.add(drugQuantityField);
        add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.add(addButton);
        southPanel.add(removeButton);
        southPanel.add(placeOrderButton);
        southPanel.add(viewSalesButton);
        add(southPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        addButton.addActionListener(e -> {
            String id = drugIdField.getText();
            String name = drugNameField.getText();
            double price = 0;
            String category = (String) drugCategoryComboBox.getSelectedItem();
            int quantity = 0;

            // Validate inputs
            if (id.isEmpty() ||!id.matches("[a-zA-Z0-9]+")) {
                JOptionPane.showMessageDialog(this, "Invalid drug ID. Please use alphanumeric characters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Drug name cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                price = Double.parseDouble(drugPriceField.getText());
                if (price <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than zero.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                quantity = Integer.parseInt(drugQuantityField.getText());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity format. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the pharmacy has space for the new drug
            if (pharmacy.getCurrentCapacity() < pharmacy.getCapacity()) {
                if (pharmacy.addDrug(new Drug(id, name, price, category, quantity))) {
                    JOptionPane.showMessageDialog(this, "Drug added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add drug. Pharmacy is full.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "The pharmacy is full. Cannot add more drugs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            drugIdField.setText("");
            drugNameField.setText("");
            drugPriceField.setText("");
            drugCategoryComboBox.setSelectedIndex(0);
            drugQuantityField.setText("");
        });
        removeButton.addActionListener(e -> {
            String idToRemove = JOptionPane.showInputDialog(this, "Enter the ID of the drug to remove:", "Remove Drug", JOptionPane.PLAIN_MESSAGE);
            if (idToRemove!= null &&!idToRemove.isEmpty()) {
                if (pharmacy.getCapacity() > pharmacy.getCurrentCapacity()) { // Check if there's space for a new drug
                    if (pharmacy.removeDrug(idToRemove)) {
                        JOptionPane.showMessageDialog(this, "Drug removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Drug not found or could not be removed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "The pharmacy is full. Cannot remove more drugs.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        placeOrderButton.addActionListener(e -> {
            String id = drugIdField.getText();
            int quantity = Integer.parseInt(drugQuantityField.getText());
            Drug drug = null;
            try {
                drug = pharmacy.findDrugById(id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if (drug!= null) {
                double price = drug.getPrice();
                String category = drug.getCategory();
                if ("Cosmetics".equals(category)) {
                    price *= 1.2; // Apply discount for cosmetics
                }
                double totalPrice = price * quantity;
                pharmacy.placeOrder(id, quantity);
                JOptionPane.showMessageDialog(this, "Total price for this order: $" + totalPrice, "Order Details", JOptionPane.INFORMATION_MESSAGE);

                // Calculate and display total sales for the day
                double totalSales = pharmacy.getTotalSales();
                JOptionPane.showMessageDialog(this, "Total sales for today: $" + totalSales, "Total Sales", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(this, "Price per unit: $" + price, "Price Information", JOptionPane.INFORMATION_MESSAGE);
                if (!"Prescription".equals(category)) {
                    // No prescription needed for non-prescription drugs
                    pharmacy.placeOrder(id, quantity);
                    JOptionPane.showMessageDialog(this, "Order placed successfully.", "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Ask for prescription
                    String prescription = JOptionPane.showInputDialog(this, "Please enter the prescription:", "Prescription Required", JOptionPane.PLAIN_MESSAGE);
                    if (prescription!= null &&!prescription.isEmpty()) {
                        pharmacy.placeOrder(id, quantity);
                        JOptionPane.showMessageDialog(this, "Order placed successfully.", "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Prescription required but not provided.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Drug not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pharmacy.getTotalSales();
                salesReportArea.setText("Total Sales: " + pharmacy.getTotalSales());
            }
        });

        // Set up layout, size, and visibility
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the capacity of the pharmacy:");
        int capacity = scanner.nextInt();
        SwingUtilities.invokeLater(() -> new PharmacyApp(capacity));
       scanner.close();
    }


    }


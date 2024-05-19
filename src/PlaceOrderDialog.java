import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;

public class PlaceOrderDialog extends JDialog {
    private Pharmacy pharmacy;
    private List<OrderItem> cart;
    private JPanel contentPane;
    private JButton buttonBuy;
    private JButton buttonCancel;
    private JFormattedTextField idField;
    private JFormattedTextField quantityField;
    private JButton buttonAdd;
    private JButton buttonViewCart;

    public PlaceOrderDialog(Frame parent, boolean modal, Pharmacy pharmacy) {
        super(parent, modal);
        this.pharmacy = pharmacy;
        cart = new ArrayList<>();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and Feel
        } catch (
                ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e
        ) {
            e.printStackTrace();
        }
        setTitle("Place an order");
        setSize(480, 240);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonBuy);

        buttonBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBuy();
            }
        });

        buttonViewCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onViewCart();
            }
        });

        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        DecimalFormat intFormat = new DecimalFormat("###0");
        intFormat.setParseIntegerOnly(true);

        NumberFormatter unsignedFormatter = new NumberFormatter(intFormat);
        unsignedFormatter.setMinimum(0);

        idField.setFormatterFactory(new DefaultFormatterFactory(unsignedFormatter));
        quantityField.setFormatterFactory(new DefaultFormatterFactory(unsignedFormatter));
    }
    private void onBuy(){
        if (cart.size() == 0) {
            JOptionPane.showMessageDialog(
                    this, "Cart is empty", "Error", JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        for (OrderItem order : cart)
            pharmacy.placeOrder(order.getDrugId(), order.getQuantity());

        cart.clear();
        JOptionPane.showMessageDialog(
                this,
                "Purchase completed.",
                "Success", JOptionPane.INFORMATION_MESSAGE
        );
        dispose();
    }

    private void onAdd() {
        try {
            String id = idField.getText();
            int quantityToBuy = Integer.parseInt(quantityField.getText());

            Drug drug = pharmacy.findDrugById(id);
            if (drug == null)
                JOptionPane.showMessageDialog(
                        this,
                        "A drug with the specified ID does not exist.",
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            else if (drug.getAvailableQuantity() == 0)
                JOptionPane.showMessageDialog(
                        this,
                        String.format("The pharmacy has unfortunately run out of %s.", drug.getName()),
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            else if (drug.getAvailableQuantity() < quantityToBuy)
                JOptionPane.showMessageDialog(
                        this,
                        String.format("The pharmacy does not have enough %s.", drug.getName()),
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            else {
                cart.add(new OrderItem(id, quantityToBuy));
                JOptionPane.showMessageDialog(
                        this,
                        String.format("Added to %s cart.", drug.getName()),
                        "Success", JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid/incomplete information. Failed to add drug to cart.",
                    "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onViewCart() {
        StringBuilder cartContents = new StringBuilder("Cart Contents:\n");
        double totalCartPrice = 0.0;
        for (OrderItem item : cart) {
            Drug drug = pharmacy.findDrugById(item.getDrugId());
            if (drug!= null) {
                double basePrice = drug.getPrice();
                double surchargedPrice = basePrice;
                if ("cosmetics".equalsIgnoreCase(drug.getCategory())) {
                    surchargedPrice *= 1.2; // Apply 20% surcharge
                }
                double itemTotalPrice = surchargedPrice * item.getQuantity();
                totalCartPrice += itemTotalPrice;
                cartContents.append("Name: ").append(drug.getName())
                            .append(", Base Price: ").append(basePrice)
                            .append(" EGP, Surcharge: ").append(surchargedPrice > basePrice? "+20%" : "N/A")
                            .append(", Quantity: ").append(item.getQuantity())
                            .append(", Total: ").append(itemTotalPrice)
                            .append(" EGP\n");
            }
        }
        cartContents.append("\nTotal Cart Price: ").append(totalCartPrice).append(" EGP");
        JOptionPane.showMessageDialog(
                PlaceOrderDialog.this,
                cartContents.toString(),
                "Cart View", JOptionPane.INFORMATION_MESSAGE
        );
    }
    private void onCancel() {
        dispose();
    }
}

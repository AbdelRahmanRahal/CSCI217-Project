import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.lang.NumberFormatException;
import java.text.NumberFormat;
import java.text.DecimalFormat;


public class AddDrugDialog extends JDialog {
    private Pharmacy pharmacy;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameField;
    private JFormattedTextField idField;
    private JFormattedTextField priceField;
    private JComboBox<String> categoryMenu;
    private JFormattedTextField quantityField;

    public AddDrugDialog(Frame parent, boolean modal, Pharmacy pharmacy) {
        super(parent, modal);
        this.pharmacy = pharmacy;

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and Feel
        } catch (
                ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e
        ) {
            e.printStackTrace();
        }
        setTitle("Add drug");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

        // Configuring JFormattedTextField for idField, priceField, and quantityField
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);

        idField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        priceField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(decimalFormat)));
        quantityField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
    }

    private void onOK() {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String category = (String) categoryMenu.getSelectedItem();
            int availableQuantity = Integer.parseInt(quantityField.getText());

            Drug newDrug = new Drug(id, name, price, category, availableQuantity);
            int status = pharmacy.addDrug(newDrug);
            switch (status) {
                case 0 -> {
                    JOptionPane.showMessageDialog(
                            this, "Drug added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                }
                case -1 -> JOptionPane.showMessageDialog(
                        this, "Invalid drug details.", "Error", JOptionPane.ERROR_MESSAGE
                );
                case -2 -> JOptionPane.showMessageDialog(
                        this, "A drug by that ID already exists.", "Error", JOptionPane.ERROR_MESSAGE
                );
                case -3 -> JOptionPane.showMessageDialog(
                        this,
                        String.format(
                                "Pharmacy is at near or full capacity. Cannot add more drugs.\n" +
                                "Current capacity: %d/%d", pharmacy.getDrugs().size(), pharmacy.getCapacity()),
                        "Error", JOptionPane.ERROR_MESSAGE
                );
                default -> JOptionPane.showMessageDialog(
                        this, "Unknown error occurred.", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Invalid/incomplete information. Failed to add drug.", "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onCancel() {
        dispose();
    }
}

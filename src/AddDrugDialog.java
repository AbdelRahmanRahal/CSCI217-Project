import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;


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
        super(parent, modal); // Pass the Pharmacy instance to the superclass constructor
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
        String id = idField.getText();
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        String category = (String) categoryMenu.getSelectedItem();
        int availableQuantity = Integer.parseInt(quantityField.getText());

        Drug newDrug = new Drug(id, name, price, category, availableQuantity);
        if (pharmacy.addDrug(newDrug)) {
            JOptionPane.showMessageDialog(this, "Drug added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add drug.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class RemoveDrugDialog extends JDialog {
    private Pharmacy pharmacy;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField idField;
    private JLabel idLabel;

    public RemoveDrugDialog(Frame parent, boolean modal, Pharmacy pharmacy) {
        super(parent, modal);
        this.pharmacy = pharmacy;

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and Feel
        } catch (
                ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e
        ) {
            e.printStackTrace();
        }
        setTitle("Remove drug");
        setSize(480, 160);
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


        DecimalFormat intFormat = new DecimalFormat("###0");
        intFormat.setParseIntegerOnly(true);

        NumberFormatter unsignedFormatter = new NumberFormatter(intFormat);
        unsignedFormatter.setMinimum(0);

        idField.setFormatterFactory(new DefaultFormatterFactory(unsignedFormatter));
    }

    private void onOK() {
        try {
            String id = idField.getText();

            if (pharmacy.findDrugById(id) != null) {
                pharmacy.removeDrug(id);
                JOptionPane.showMessageDialog(
                        this, "Drug removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            }
            else
                JOptionPane.showMessageDialog(
                        this, "A drug with the specified ID does not exist.", "Error", JOptionPane.ERROR_MESSAGE
                );
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid/incomplete information. Failed to remove drug.",
                    "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onCancel() {
        dispose();
    }
}

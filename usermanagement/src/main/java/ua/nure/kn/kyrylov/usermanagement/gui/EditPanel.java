package ua.nure.kn.kyrylov.usermanagement.gui;

import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.domain.User;
import ua.nure.kn.kyrylov.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

public class EditPanel extends JPanel implements ActionListener {
    private MainFrame mainFrame;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton cancelButton;
    private JButton okButton;
    private JTextField firstNameField;
    private JTextField dateOfBirthField;
    private JTextField lastNameField;
    private User user;

    public EditPanel(MainFrame mainFrame, User user) {
        this.setMainFrame(mainFrame);
        setUser(user);
        initialize();
    }

    private void initialize() {
        this.setName(Messages.getString("editPanelName"));
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonsPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, Messages.getString("firstNameText"), getFirstNameField());
            addLabeledField(fieldPanel, Messages.getString("lastNameText"), getLastNameField());
            addLabeledField(fieldPanel, Messages.getString("dateOfBirthText"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName(Messages.getString("dateOfBirthFieldName"));
            dateOfBirthField.setText(getUser().getDateOfBirth()!=null?getUser().getDateOfBirth().toString():"");
        }
        return dateOfBirthField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName(Messages.getString("lastNameFieldName"));
            lastNameField.setText(getUser().getLastName());
        }
        return lastNameField;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName(Messages.getString("firstNameFieldName"));
            firstNameField.setText(getUser().getFirstName());
        }
        return firstNameField;
    }

    public JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("cancelText"));
            cancelButton.setName(Messages.getString("cancelButtonName"));
            cancelButton.setActionCommand(Messages.getString("cancelCommand"));
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    public JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText(Messages.getString("okText"));
            okButton.setName(Messages.getString("okButtonName"));
            okButton.setActionCommand(Messages.getString("okCommand"));
            okButton.addActionListener(this);
        }
        return okButton;
    }

    private void addLabeledField(JPanel fieldPanel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(field);
        fieldPanel.add(label);
        fieldPanel.add(field);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(Messages.getString("okCommand"))) {
            getUser().setFirstName(getFirstNameField().getText());
            getUser().setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();
            try {
                getUser().setDateOfBirth(format.parse(getDateOfBirthField().getText()));
            } catch (ParseException ex) {
                getDateOfBirthField().setBackground(Color.RED);
                ex.printStackTrace();
                return;
            }
            try {
                getMainFrame().getUserDao().updateUser(getUser());
            } catch (DatabaseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        clearFields();
        this.setVisible(false);
        getMainFrame().showBrowsePanel();
    }

    private void clearFields() {
        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(Color.WHITE);
        getLastNameField().setText("");
        getFirstNameField().setText("");
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public void setOkButton(JButton okButton) {
        this.okButton = okButton;
    }

    public void setFirstNameField(JTextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

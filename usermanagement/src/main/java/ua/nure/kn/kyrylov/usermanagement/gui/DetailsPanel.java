package ua.nure.kn.kyrylov.usermanagement.gui;

import ua.nure.kn.kyrylov.usermanagement.domain.User;
import ua.nure.kn.kyrylov.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailsPanel extends JPanel implements ActionListener {
    private MainFrame mainFrame;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton okButton;
    private JLabel fullNameField;
    private JLabel ageField;
    private User user;

    public DetailsPanel(MainFrame mainFrame, User user) {
        this.setMainFrame(mainFrame);
        setUser(user);
        initialize();
    }

    private void initialize() {
        this.setName(Messages.getString("detailsPanelName"));
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonsPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
        }
        return buttonPanel;
    }

    private JPanel getFieldPanel() {
        fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(2, 2));
        addLabeledField(fieldPanel, Messages.getString("fullNameText"), getFullNameField());
        addLabeledField(fieldPanel, Messages.getString("ageText"), getAgeField());
        return fieldPanel;
    }

    private JLabel getAgeField() {
        if (ageField == null) {
            ageField = new JLabel();
            ageField.setName(Messages.getString("ageFieldName"));
        }
        ageField.setText(getUser().getAgeStr());
        return ageField;
    }

    private JLabel getFullNameField() {
        if (fullNameField == null) {
            fullNameField = new JLabel();
            fullNameField.setName(Messages.getString("fullNameFieldName"));
        }
        fullNameField.setText(getUser().getFullName());
        return fullNameField;
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

    private void addLabeledField(JPanel fieldPanel, String labelText, JLabel field) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(field);
        fieldPanel.add(label);
        fieldPanel.add(field);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clearFields();
        this.setVisible(false);
        getMainFrame().showBrowsePanel();
    }

    private void clearFields() {
        getFullNameField().setText("");
        getAgeField().setText("");
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

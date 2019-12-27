package ua.nure.kn.kyrylov.usermanagement.gui;

import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.domain.User;
import ua.nure.kn.kyrylov.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePanel extends JPanel implements ActionListener {
    private MainFrame mainFrame;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton cancelButton;
    private JButton okButton;
    private JLabel deleteTextField;
    private User user;

    public DeletePanel(MainFrame mainFrame, User user) {
        this.setMainFrame(mainFrame);
        setUser(user);
        initialize();
    }

    private void initialize() {
        this.setName(Messages.getString("deletePanelName"));
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
            fieldPanel.setLayout(new BorderLayout());
            fieldPanel.add(getDeleteTextField());
        }
        return fieldPanel;
    }

    private JLabel getDeleteTextField() {
        if (deleteTextField == null) {
            deleteTextField = new JLabel();
            deleteTextField.setName(Messages.getString("deleteText"));
            deleteTextField.setText(Messages.getString("deleteText"));
        }
        return deleteTextField;
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


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(Messages.getString("okCommand"))) {
            try {
                getMainFrame().getUserDao().deleteUser(getUser());
            } catch (DatabaseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.setVisible(false);
        getMainFrame().showBrowsePanel();
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

package ua.nure.kn.kyrylov.usermanagement.gui;

import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowsePanel extends JPanel implements ActionListener {

    private MainFrame mainFrame;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton detailsButton;
    private JButton deleteButton;
    private JButton editButton;
    private JScrollPane tablePanel;
    private JTable userTable;

    public BrowsePanel(MainFrame mainFrame) {
        this.setMainFrame(mainFrame);
        initialize();
    }

    private void initialize() {
        this.setName(Messages.getString("browsePanelName"));
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            setTablePanel(new JScrollPane(getUserTable()));
        }
        return tablePanel;
    }

    public JTable getUserTable() {
        if (userTable == null) {
            setUserTable(new JTable());
            userTable.setName(Messages.getString("userTableName"));
        }
        return userTable;
    }

    public void initTable() {
        UserTableModel model = null;
        try {
            model = new UserTableModel(getMainFrame().getUserDao().findAllUsers());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList<>());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(model);
    }

    private JButton getAddButton() {
        if (addButton == null) {
            setAddButton(new JButton());
            addButton.setText(Messages.getString("addText"));
            addButton.setName(Messages.getString("addButtonName"));
            addButton.setActionCommand(Messages.getString("addCommand"));
            addButton.addActionListener(this);
        }
        return addButton;
    }

    public JButton getDetailsButton() {
        if (detailsButton == null) {
            setDetailsButton(new JButton());
            detailsButton.setText(Messages.getString("detailsText"));
            detailsButton.setName(Messages.getString("detailsButtonName"));
            detailsButton.setActionCommand(Messages.getString("detailsCommand"));
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    public JButton getDeleteButton() {
        if (deleteButton == null) {
            setDeleteButton(new JButton());
            deleteButton.setText(Messages.getString("deleteText"));
            deleteButton.setName(Messages.getString("deleteButtonName"));
            deleteButton.setActionCommand(Messages.getString("deleteCommand"));
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    public JButton getEditButton() {
        if (editButton == null) {
            setEditButton(new JButton());
            editButton.setText(Messages.getString("editText"));
            editButton.setName(Messages.getString("editButtonName"));
            editButton.setActionCommand(Messages.getString("editCommand"));
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JPanel getButtonsPanel() {
        if (getButtonPanel() == null) {
            setButtonPanel(new JPanel());
            getButtonPanel().add(getAddButton(), null);
            getButtonPanel().add(getEditButton(), null);
            getButtonPanel().add(getDeleteButton(), null);
            getButtonPanel().add(getDetailsButton(), null);
        }
        return getButtonPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String addCommand = Messages.getString("addCommand");
        String deleteCommand = Messages.getString("deleteCommand");
        String editCommand = Messages.getString("editCommand");
        String detailsCommand = Messages.getString("detailsCommand");

        if (addCommand.equalsIgnoreCase(e.getActionCommand())) {
            this.setVisible(false);
            getMainFrame().showAddPanel();
        } else if (editCommand.equalsIgnoreCase(e.getActionCommand()) && userTable.getSelectedRow() != -1) {
            this.setVisible(false);
            getMainFrame().showEditPanel((Long) getUserTable().getValueAt(userTable.getSelectedRow(), 0));
        } else if (deleteCommand.equalsIgnoreCase(e.getActionCommand()) && userTable.getSelectedRow() != -1) {
            this.setVisible(false);
            getMainFrame().showDeletePanel((Long) getUserTable().getValueAt(userTable.getSelectedRow(), 0));
        } else if (detailsCommand.equalsIgnoreCase(e.getActionCommand()) && userTable.getSelectedRow() != -1) {
            this.setVisible(false);
            getMainFrame().showDetailsPanel((Long) getUserTable().getValueAt(userTable.getSelectedRow(), 0));
        }
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public void setDetailsButton(JButton detailsButton) {
        this.detailsButton = detailsButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public void setEditButton(JButton editButton) {
        this.editButton = editButton;
    }

    public void setTablePanel(JScrollPane tablePanel) {
        this.tablePanel = tablePanel;
    }

    public void setUserTable(JTable userTable) {
        this.userTable = userTable;
    }

}

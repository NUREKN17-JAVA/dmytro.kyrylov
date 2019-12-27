package ua.nure.kn.kyrylov.usermanagement.gui;

import ua.nure.kn.kyrylov.usermanagement.domain.User;
import ua.nure.kn.kyrylov.usermanagement.util.Messages;

import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private List<User> userList;

    private static final String[] COLUMN_NAMES = {Messages.getString("id"),
            Messages.getString("firstNameText"), Messages.getString("lastNameText")};
    private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};

    public UserTableModel(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int getRowCount() {
        return getUserList().size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = getUserList().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
        }
        return null;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUsers(Collection users) {
        if (getUserList() == null) {
            setUserList(new LinkedList<>());
        }
        getUserList().addAll(users);
    }

    public void clearUsers() {
        if (getUserList() == null) {
            setUserList(new LinkedList<>());
        }
        getUserList().clear();
    }
}

package ua.nure.kn.kyrylov.usermanagement.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.kyrylov.usermanagement.db.classes.MockDaoFactory;
import ua.nure.kn.kyrylov.usermanagement.db.classes.utils.DaoFactory;
import ua.nure.kn.kyrylov.usermanagement.domain.User;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.List;
import java.util.*;

public class MainFrameTest extends JFCTestCase {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final Date DATE_OF_BIRTH = new Date();
    private MainFrame mainFrame;
    private Mock mockUserDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        try {
            Properties properties = new Properties();
            properties.setProperty("dao.factory", MockDaoFactory.class.getName());
            DaoFactory.init(properties);
            setMockUserDao(((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao());
            getMockUserDao().expectAndReturn("findAllUsers", new ArrayList<>());

            setHelper(new JFCTestHelper());
            setMainFrame(new MainFrame());
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        getMainFrame().setVisible(true);
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            getMockUserDao().verify();
            getMainFrame().setVisible(false);
            getHelper().cleanUp(this);
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");

        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(3, table.getColumnCount());
        assertEquals("ID", table.getColumnName(0));
        assertEquals("First Name", table.getColumnName(1));
        assertEquals("Last Name", table.getColumnName(2));

        find(JTable.class, "userTable");
        find(JButton.class, "addButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "editButton");
        find(JButton.class, "detailsButton");

    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(getMainFrame(), 0);
        assertNotNull("Could not find component " + name + "", component);
        return component;
    }

    public void testAddUser() {
        try {
            User user = new User(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
            User expectedUser = new User(1L, FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
            getMockUserDao().expectAndReturn("createUser", user, expectedUser);

            List<User> userList = new LinkedList<>();
            userList.add(expectedUser);
            getMockUserDao().expectAndReturn("findAllUsers", userList);

            JTable userTable = (JTable) find(JTable.class, "userTable");
            assertEquals(0, userTable.getRowCount());

            JButton addButton = (JButton) find(JButton.class, "addButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, "addPanel");

            JButton okButton = (JButton) find(JButton.class, "okButton");
            find(JButton.class, "cancelButton");

            fillFields();

            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, "browsePanel");
            userTable = (JTable) find(JTable.class, "userTable");
            assertEquals(1, userTable.getRowCount());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testAddUserCancel() {
        User user = new User(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
        User expectedUser = new User(1L, FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);

        getMockUserDao().expectAndReturn("createUser", user, expectedUser);

        ArrayList users = new ArrayList();
        users.add(expectedUser);
        getMockUserDao().expectAndReturn("findAllUsers", users);
        ;

        find(JPanel.class, "browsePanel");
        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(0, table.getRowCount());

        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
        find(JPanel.class, "addPanel");
        fillFields();

        JButton cancelButton = (JButton) find(JButton.class, "cancelButton");

        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
        find(JPanel.class, "browsePanel");
        table = (JTable) find(JTable.class, "userTable");
        assertEquals(0, table.getRowCount());
    }

    public void testDeleteUser() {
        try {
            User expectedUser = new User(1L, FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
            getMockUserDao().expectAndReturn("deleteUser", expectedUser);
            ArrayList<User> users = new ArrayList<User>();
            getMockUserDao().expectAndReturn("findAllUsers", users);
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(0, table.getRowCount());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testDetailsUser() {
        User expectedUser = new User(1L, FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);

        ArrayList<User> users = new ArrayList<User>();
        getMockUserDao().expectAndReturn("findAllUsers", users);

        JTable table = (JTable) this.find(JTable.class, "userTable");
        assertEquals(1, table.getRowCount());

        JButton detailsButton = (JButton) this.find(JButton.class, "detailsButton");
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
        getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

        this.find(JPanel.class, "detailsPanel");

        this.find(JLabel.class, "ageFieldName");
        this.find(JLabel.class, "fullNameFieldName");
        //можно было-бы проверить и содержимое строк
        JButton okButton = (JButton) this.find(JButton.class, "okButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        this.find(JPanel.class, "browsePanel");
        table = (JTable) this.find(JTable.class, "userTable");
        assertEquals(1, table.getRowCount());
    }

    public void testEditUserOk() {
        User user = new User(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
        User expectedUser = new User(1L, FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
        getMockUserDao().expectAndReturn("createUser", user, expectedUser);

        List<User> userList = new LinkedList<>();
        userList.add(expectedUser);
        getMockUserDao().expectAndReturn("findAllUsers", userList);

        find(JPanel.class, "browsePanel");

        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());
        JButton editButton = (JButton) find(JButton.class, "editButton");
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
        getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

        find(JPanel.class, "editPanel");
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        getHelper().sendString(new StringEventData(this, firstNameField, "Dima"));
        getHelper().sendString(new StringEventData(this, lastNameField, "Kirillov"));

        JButton okButton = (JButton) find(JButton.class, "okButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));


        find(JPanel.class, "browsePanel");
        userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(2, userTable.getRowCount());
    }

    public void testEditUserCancel() {
        User user = new User(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
        User expectedUser = new User(1L, FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
        getMockUserDao().expectAndReturn("createUser", user, expectedUser);

        List<User> userList = new LinkedList<>();
        userList.add(expectedUser);
        getMockUserDao().expectAndReturn("findAllUsers", userList);

        find(JPanel.class, "browsePanel");

        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());
        JButton editButton = (JButton) find(JButton.class, "editButton");
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
        getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

        find(JPanel.class, "editPanel");
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        getHelper().sendString(new StringEventData(this, firstNameField, "Dima"));
        getHelper().sendString(new StringEventData(this, lastNameField, "Kirillov"));

        JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));


        find(JPanel.class, "browsePanel");
        userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(2, userTable.getRowCount());
    }

    private void fillFields() {
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");

        getHelper().sendString(new StringEventData(this, firstNameField, FIRST_NAME));
        getHelper().sendString(new StringEventData(this, lastNameField, LAST_NAME));
        getHelper().sendString(new StringEventData(this, dateOfBirthField, DateFormat.getInstance().format(DATE_OF_BIRTH)));
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }
}

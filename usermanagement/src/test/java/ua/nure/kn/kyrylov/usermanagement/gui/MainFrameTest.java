package ua.nure.kn.kyrylov.usermanagement.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.kyrylov.usermanagement.db.classes.MockDaoFactory;
import ua.nure.kn.kyrylov.usermanagement.db.classes.utils.DaoFactory;
import ua.nure.kn.kyrylov.usermanagement.domain.User;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

public class MainFrameTest extends JFCTestCase {

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
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();
            User user = new User(firstName, lastName, now);
            User expectedUser = new User(new Long(1), firstName, lastName, now);
            getMockUserDao().expectAndReturn("createUser", user, expectedUser);

            List<User> userList = new LinkedList<>();
            userList.add(expectedUser);
            getMockUserDao().expectAndReturn("findAllUsers",userList);

            JTable userTable = (JTable) find(JTable.class, "userTable");
            assertEquals(0, userTable.getRowCount());

            JButton addButton = (JButton) find(JButton.class, "addButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, "addPanel");

            JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
            JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
            JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
            JButton okButton = (JButton) find(JButton.class, "okButton");
            find(JButton.class, "cancelButton");

            getHelper().sendString(new StringEventData(this, firstNameField, firstName));
            getHelper().sendString(new StringEventData(this, lastNameField, lastName));
            DateFormat format = DateFormat.getDateInstance();
            String date = format.format(now);
            getHelper().sendString(new StringEventData(this, dateOfBirthField, date));

            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, "browsePanel");
            userTable = (JTable) find(JTable.class, "userTable");
            assertEquals(1, userTable.getRowCount());
        } catch (Exception e) {
            fail(e.toString());
        }
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

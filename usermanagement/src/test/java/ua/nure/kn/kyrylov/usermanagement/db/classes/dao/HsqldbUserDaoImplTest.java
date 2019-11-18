package ua.nure.kn.kyrylov.usermanagement.db.classes.dao;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import ua.nure.kn.kyrylov.usermanagement.User;
import ua.nure.kn.kyrylov.usermanagement.db.classes.utils.DAOFactory;
import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.utils.ConnectionFactory;

import java.util.Collection;
import java.util.Date;

public class HsqldbUserDaoImplTest extends DatabaseTestCase {

    private static final long USER_ID = 1000L;

    private UserDao userDao;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
        return new DatabaseConnection(userDao.getConnectionFactory().createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(getClass().getClassLoader()
                .getResourceAsStream("userdataset.xml"));
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testCreateUser() {
        User user = new User();
        user.setDateOfBirth(new Date());
        user.setFirstName("Dmytro");
        user.setLastName("Kyrylov");
        assertNull(user.getId());
        try {
            user = getUserDao().createUser(user);
        } catch (DatabaseException e) {
            fail(e.toString());
        }
        assertNotNull(user.getId());
    }

    public void testUpdateUser() {
        try {
            User user = getUserDao().findUser(USER_ID);
            assertNotNull(user);
            String lastName = user.getLastName() + "temp";
            user.setLastName(lastName);
            getUserDao().updateUser(user);
            user = getUserDao().findUser(USER_ID);
            assertEquals(lastName, user.getLastName());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testDeleteUser() {
        try {
            User user = getUserDao().findUser(USER_ID);
            assertNotNull(user);
            getUserDao().deleteUser(user);
            user = getUserDao().findUser(USER_ID);
            assertNull(user);
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testFindUser() {
        try {
            User user = getUserDao().findUser(USER_ID);
            assertNotNull(user);
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testFindAllUsers() {

        Collection collection = null;
        try {
            collection = getUserDao().findAllUsers();
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
        assertNotNull("Collection is null", collection);
        assertEquals("Collection size.", 2, collection.size());
    }

    private UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
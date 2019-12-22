package ua.nure.kn.kyrylov.usermanagement.db.classes.utils;

import junit.framework.TestCase;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;

public class DaoFactoryTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetUserDao() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull(daoFactory);
            UserDao userDao = daoFactory.getUserDao();
            assertNotNull(userDao);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
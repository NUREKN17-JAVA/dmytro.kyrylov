package ua.nure.kn.kyrylov.usermanagement.db.classes;

import com.mockobjects.dynamic.Mock;
import ua.nure.kn.kyrylov.usermanagement.db.classes.utils.DaoFactory;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;

public class MockDaoFactory extends DaoFactory {

    private Mock mockUserDao;

    public MockDaoFactory() {
        setMockUserDao(new Mock(UserDao.class));
    }

    @Override
    public UserDao getUserDao() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (UserDao) getMockUserDao().proxy();
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    private void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }
}

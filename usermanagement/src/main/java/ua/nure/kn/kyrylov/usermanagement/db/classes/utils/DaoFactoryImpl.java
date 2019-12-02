package ua.nure.kn.kyrylov.usermanagement.db.classes.utils;

import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;

public class DaoFactoryImpl extends DaoFactory {
    private static final String USER_DAO = "ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao";

    @Override
    public UserDao getUserDao() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        UserDao userDao;
        Class clazz = Class.forName(getProperties().getProperty(USER_DAO));
        userDao = (UserDao) clazz.newInstance();
        userDao.setConnectionFactory(getConnectionFactory());
        return userDao;
    }
}

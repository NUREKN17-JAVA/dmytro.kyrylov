package ua.nure.kn.kyrylov.usermanagement.db.classes.utils;

import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.utils.ConnectionFactory;

import java.io.IOException;
import java.util.Properties;

public class DAOFactory {

    private static final String USER_DAO = "ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao";

    private final Properties properties;

    private final static DAOFactory INSTANCE = new DAOFactory();

    private DAOFactory() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConnectionFactory getConnectionFactory() {
        String user = getProperties().getProperty("connection.user");
        String password = getProperties().getProperty("connection.password");
        String url = getProperties().getProperty("connection.url");
        String driver = getProperties().getProperty("connection.driver");
        return new ConnectionFactoryImpl(driver, url, user, password);
    }

    public UserDao getUserDao() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        UserDao userDao;
        Class clazz = Class.forName(getProperties().getProperty(USER_DAO));
        userDao = (UserDao) clazz.newInstance();
        userDao.setConnectionFactory(getConnectionFactory());
        return userDao;
    }

    private Properties getProperties() {
        return properties;
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }
}

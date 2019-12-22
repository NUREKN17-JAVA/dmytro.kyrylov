package ua.nure.kn.kyrylov.usermanagement.db.classes.utils;

import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.utils.ConnectionFactory;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {

    private static Properties properties;

    private static final String DAO_FACTORY = "dao.factory";

    private static DaoFactory instance;

    static {
        setProperties(new Properties());
        try {
            getProperties().load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected DaoFactory() {

    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            try {
                Class factoryClass = Class.forName(getProperties().getProperty(DAO_FACTORY));
                setInstance((DaoFactory) factoryClass.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public static void init(Properties properties) {
        setProperties(properties);
        setInstance(null);
    }

    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(getProperties());
    }

    public abstract UserDao getUserDao() throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    protected static Properties getProperties() {
        return properties;
    }

    protected static void setProperties(Properties properties) {
        DaoFactory.properties = properties;
    }

    private static void setInstance(DaoFactory instance) {
        DaoFactory.instance = instance;
    }

}

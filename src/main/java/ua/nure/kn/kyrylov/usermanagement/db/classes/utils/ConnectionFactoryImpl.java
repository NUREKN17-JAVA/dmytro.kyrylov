package ua.nure.kn.kyrylov.usermanagement.db.classes.utils;

import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public ConnectionFactoryImpl(Properties properties) {
        this.user = properties.getProperty("connection.user");
        this.password = properties.getProperty("connection.password");
        this.url = properties.getProperty("connection.url");
        this.driver = properties.getProperty("connection.driver");
    }

    @Override
    public Connection createConnection() throws DatabaseException {
        try {
            Class.forName(getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
        try {
            return DriverManager.getConnection(getUrl(), getUser(), getPassword());
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private String getDriver() {
        return driver;
    }

    private String getUrl() {
        return url;
    }

    private String getUser() {
        return user;
    }

    private String getPassword() {
        return password;
    }

}

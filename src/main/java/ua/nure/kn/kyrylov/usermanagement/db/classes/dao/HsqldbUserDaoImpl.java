package ua.nure.kn.kyrylov.usermanagement.db.classes.dao;

import ua.nure.kn.kyrylov.usermanagement.domain.User;
import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.utils.ConnectionFactory;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class HsqldbUserDaoImpl implements UserDao {

    private ConnectionFactory connectionFactory;
    private Connection connection;

    private static final String INSERT_PREPARED_STATEMENT =
            "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";

    private static final String UPDATE_PREPARED_STATEMENT_STR =
            "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";

    private static final String DELETE_PREPARED_STATEMENT_STR =
            "DELETE FROM users WHERE id = ?";

    private static final String FIND_PREPARED_STATEMENT_STR =
            "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id = ?";

    private static final String FIND_PREPARED_STATEMENT__AGENT_STR =
            "SELECT id, firstname, lastname, dateofbirth FROM users WHERE firstname = ? and lastname = ?";

    private static final String GET_ID_CALLABLE_STATEMENT = "call IDENTITY()";

    private static final String SELECT_ALL_USERS_QUERY =
            "SELECT id, firstname, lastname, dateofbirth FROM users";

    public HsqldbUserDaoImpl() {
    }

    public HsqldbUserDaoImpl(ConnectionFactory connectionFactory) throws DatabaseException {
        this.connectionFactory = connectionFactory;
        this.connection = connectionFactory.createConnection();
    }

    @Override
    public User createUser(User user) throws DatabaseException {
        try {
            PreparedStatement statement = getConnectionInstance().prepareStatement(INSERT_PREPARED_STATEMENT);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));

            int resultOfQuery = statement.executeUpdate();
            if (resultOfQuery != 1) {
                throw new DatabaseException("Number of the inserted rows: " + resultOfQuery);
            }

            CallableStatement callableStatement = getConnectionInstance().prepareCall(GET_ID_CALLABLE_STATEMENT);
            ResultSet keys = callableStatement.executeQuery();

            if (!keys.next()) {
                throw new DatabaseException("Id for user was not found");
            }
            user.setId(keys.getLong(1));

            statement.close();
            callableStatement.close();
            keys.close();
            getConnection().close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return user;
    }

    @Override
    public void updateUser(User user) throws DatabaseException {
        try {
            PreparedStatement preparedStatement = getConnectionInstance().prepareStatement(UPDATE_PREPARED_STATEMENT_STR);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            preparedStatement.setLong(4, user.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            getConnection().close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void deleteUser(User user) throws DatabaseException {
        try {
            PreparedStatement preparedStatement = getConnectionInstance().prepareStatement(DELETE_PREPARED_STATEMENT_STR);
            preparedStatement.setLong(1, user.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            getConnection().close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public User findUser(Long id) throws DatabaseException {
        User user = null;
        try {
            PreparedStatement statement = getConnectionInstance().prepareStatement(FIND_PREPARED_STATEMENT_STR);
            statement.setLong(1, id);

            ResultSet users = statement.executeQuery();

            if (!users.next()) {
                return user;
            }
            user = getUserFromResultSet(users);

            statement.close();
            users.close();
            getConnection().close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() throws DatabaseException {
        List<User> result = new LinkedList<>();
        try {
            Statement statement = getConnectionInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS_QUERY);
            while (resultSet.next()) {
                result.add(getUserFromResultSet(resultSet));
            }
            getConnection().close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return result;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setFirstName(resultSet.getString(2));
        user.setLastName(resultSet.getString(3));
        user.setDateOfBirth(new java.util.Date(resultSet.getDate(4).getTime()));
        return user;
    }

    @Override
    public List<User> find(String firstName, String lastName) throws DatabaseException {
        List<User> result = new LinkedList<>();
        try {
            PreparedStatement statement = getConnectionInstance().prepareStatement(FIND_PREPARED_STATEMENT__AGENT_STR);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(getUserFromResultSet(resultSet));
            }
            getConnection().close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return result;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    private Connection getConnectionInstance() throws DatabaseException, SQLException {
        if (getConnection() == null || getConnection().isClosed()) {
            setConnection(getConnectionFactory().createConnection());
        }
        return getConnection();
    }

    private Connection getConnection() {
        return connection;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }
}

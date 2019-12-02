package ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao;

import ua.nure.kn.kyrylov.usermanagement.domain.User;
import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.utils.ConnectionFactory;

import java.util.Collection;
import java.util.List;

public interface UserDao {
    User createUser(User user) throws DatabaseException;

    void updateUser(User user) throws DatabaseException;

    void deleteUser(User user) throws DatabaseException;

    User findUser(Long id) throws DatabaseException;

    List<User> findAllUsers() throws DatabaseException;

    void setConnectionFactory(ConnectionFactory connectionFactory);

    ConnectionFactory getConnectionFactory();
}

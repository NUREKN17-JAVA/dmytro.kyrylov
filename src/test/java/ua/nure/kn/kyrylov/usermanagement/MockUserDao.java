package ua.nure.kn.kyrylov.usermanagement;

import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.utils.ConnectionFactory;
import ua.nure.kn.kyrylov.usermanagement.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class MockUserDao implements UserDao {
    private long id = 0;
    private Map<Long, User> userMap = new HashMap<>();

    @Override
    public User createUser(User user) throws DatabaseException {
        user.setId(++id);
        getUserMap().put(user.getId(), user);
        return user;
    }

    @Override
    public void updateUser(User user) throws DatabaseException {
        Long id = user.getId();
        getUserMap().remove(id);
        getUserMap().put(id, user);
    }

    @Override
    public void deleteUser(User user) throws DatabaseException {
        getUserMap().remove(user.getId());
    }

    @Override
    public User findUser(Long id) throws DatabaseException {
        return getUserMap().get(id);
    }

    @Override
    public List<User> findAllUsers() throws DatabaseException {
        return new ArrayList<>(getUserMap().values());
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }

    @Override
    public List<User> find(String firstName, String lastName) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return null;
    }

    public Map<Long, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Long, User> userMap) {
        this.userMap = userMap;
    }
}

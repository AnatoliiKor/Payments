package org.anatkor.services;

import org.anatkor.dao.UserDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Role;
import org.anatkor.model.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserService {

    private UserDao userDao = new UserDao();

    public List<User> findAll() {
        return userDao.findAll();
    }

    public boolean addUser(String username, String email, String password) throws DBException {
        User user = new User.UserBuilder()
                .withPassword(password)
                .withUsername(username)
                .withEmail(email)
                .build();
        return userDao.addUser(user);
    }

    public User findUserByUsername(String username) throws DBException {
        return  userDao.findUserByUsername(username);
    }

    public User findUserByUsernamePassword(String username, String password) throws DBException {
        return  userDao.findUserByUsernamePassword(username, password);
    }
}

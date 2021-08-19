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

    public boolean addUser(String lastName,
                           String name,
                           String middleName,
                           String password,
                           String email,
                           long phoneNumber) throws DBException {

        User user = new User.UserBuilder()
                .withLastName(lastName)
                .withName(name)
                .withMiddleName(middleName)
                .withPassword(password)
                .withEmail(email)
                .withPhoneNumber(phoneNumber)
                .build();
        return userDao.addUser(user);
    }

    public User findUserByUsername(String username) throws DBException {
        return userDao.findUserByUsername(username);
    }

    public User findUserByUsernamePassword(String username, String password) throws DBException {
        return userDao.findUserByUsernamePassword(username, password);
    }

    public User findUserById(Long id) throws DBException {
        return userDao.findUserById(id);
    }
}

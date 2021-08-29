package org.anatkor.services;

import org.anatkor.dao.UserDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;

import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();

    public List<User> findAll() {
        return userDao.findAll();
    }

    public boolean addUser(String lastName, String name, String middleName, String password, String email,
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

    public User findUserByPhoneAndPassword(Long phoneNumber, String password) throws DBException {
        return userDao.findUserByPhoneAndPassword(phoneNumber, password);
    }

    public User findUserById(Long id) throws DBException {
        return userDao.findUserById(id);
    }

    public boolean updateUserStatus(User user) {
        return userDao.updateUserStatus(user);
    }
}

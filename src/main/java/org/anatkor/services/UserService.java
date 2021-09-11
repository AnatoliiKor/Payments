package org.anatkor.services;

import org.anatkor.dao.UserDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;

import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();
    private AccountService accountService = new AccountService();

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
        User user = userDao.findUserByPhoneAndPassword(phoneNumber, password);
        user.setAccountNumbers(accountService.findAllAccountNumbersByUserId(user.getId()));
        return user;
    }

    public User findUserById(Long id) throws DBException {
        return userDao.findUserById(id);
    }

    public String findUserFullNameByAccounNumber(Long accountNumber) throws DBException {
        return userDao.findUserFullNameByAccountNumber(accountNumber);
    }

    public boolean updateUserStatus(User user) {
        return userDao.updateUserStatus(user);
    }
}

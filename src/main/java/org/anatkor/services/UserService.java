package org.anatkor.services;

import org.anatkor.constants.Constant;
import org.anatkor.dao.UserDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserService {

    private final UserDao userDao = new UserDao();
    private final AccountService accountService = new AccountService();

    public List<User> findAll() {
        return userDao.findAll();
    }

    public boolean addUser(User user) throws DBException {
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

    public String findUserFullNameByAccountNumber(Long accountNumber) throws DBException {
        return userDao.findUserFullNameByAccountNumber(accountNumber);
    }

    public boolean updateUserStatus(User user) {
        return userDao.updateUserStatus(user);
    }

    public User userFromRequest(HttpServletRequest req) {
        String lastName = req.getParameter(Constant.LAST_NAME);
        String name = req.getParameter(Constant.NAME);
        String middleName = req.getParameter(Constant.MIDDLE_NAME);
        if (middleName == null) {
            middleName = "";
        }
        String password = req.getParameter(Constant.PASSWORD);
        String email = req.getParameter(Constant.EMAIL);
        String phoneNumber = req.getParameter(Constant.PHONE_NUMBER);
        if (lastName != null && !lastName.equals("")
                && name != null && !name.equals("")
                && password != null && !password.equals("")
                && email != null && !email.equals("")
                && phoneNumber != null && !phoneNumber.equals("")
        ) {
            return new User.UserBuilder()
                    .withLastName(lastName)
                    .withName(name)
                    .withMiddleName(middleName)
                    .withPassword(password)
                    .withEmail(email)
                    .withPhoneNumber(Long.parseLong("38" + phoneNumber))
                    .build();
        }
        return null;
    }
}

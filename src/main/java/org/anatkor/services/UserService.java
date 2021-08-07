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

    public void addUser(String username, String email, String password) throws DBException, SQLException {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        User user = new User.UserBuilder()
                .withPassword(password)
                .withUsername(username)
                .withEmail(email)
                .withRegistrationDateTime(LocalDateTime.now())
                .withActive(true)
                .withRoles(roles)
                .build();
//        Long id = userDao.addUser(user);
        userDao.addUser(user);
//        userDao.addUserRole(id);
    }

    public User findUserByUsername(String username) throws DBException {
        return  userDao.findUserByUsername(username);
    }
}

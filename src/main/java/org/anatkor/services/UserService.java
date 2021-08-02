package org.anatkor.services;

import org.anatkor.dao.UserDao;
import org.anatkor.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void addUser(String username, String email, String password) {
        User user = new User.UserBuilder()
                .withPassword(password)
                .withUsername(username)
                .withEmail(email)
                .withRegistrationDateTime(LocalDateTime.now())
                .withActive(true)
                .withRoles("USER")
                .build();
//        Long id = userDao.addUser(user);
        userDao.addUser(user);
//        userDao.addUserRole(id);


    }

}

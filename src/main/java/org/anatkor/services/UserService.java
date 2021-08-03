package org.anatkor.services;

import org.anatkor.dao.UserDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void addUser(String username, String email, String password) throws DBException {
        User user = new User.UserBuilder()
                .withPassword(password)
                .withUsername(username)
                .withEmail(email)
                .withRegistrationDateTime(LocalDateTime.now())
                .withActive(true)
                .withRoles("BIKE")
                .build();
//        Long id = userDao.addUser(user);
        userDao.addUser(user);
//        userDao.addUserRole(id);


    }

    public void newBike(String name, String brand, String category, String colour, String description, int price) {
    }
}

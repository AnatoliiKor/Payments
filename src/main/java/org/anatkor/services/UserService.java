package org.anatkor.services;

import org.anatkor.dao.UserDao;
import org.anatkor.model.User;

import java.util.List;

public class UserService {

    UserDao userDao = new UserDao();

    public List<User> findAll() {
        return userDao.findAll();
    }
}

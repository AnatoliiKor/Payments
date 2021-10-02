package org.anatkor.services;

import org.anatkor.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {
    private final UserService userService = new UserService();

    @Test
    public void findAll() {
        assertNotNull(userService.findAll());
    }

    @Test
    public void findUserByPhoneAndPasswordWithAccounts() {
        User user = userService.findUserByPhoneAndPassword(389999999999L, "1");
        assertNotNull(user.getAccountNumbers());
    }

    @Test
    public void findUserById() {
        assertNotNull(userService.findUserById(42L));
    }

    @Test
    public void findUserFullNameByAccounNumber() {
        assertNotNull(userService.findUserFullNameByAccountNumber(10000000026L));
    }
}
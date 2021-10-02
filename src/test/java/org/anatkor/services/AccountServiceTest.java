package org.anatkor.services;

import org.anatkor.model.Account;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountServiceTest {
    private final AccountService accountService = new AccountService();

    @Test
    public void findAllAccountsByUserId() {
        List<Account> accounts = accountService.findAllAccountsByUserId(42L);
        assertEquals(6, accounts.size());
    }

    @Test
    public void findAllAccountNumbersByUserId() {
        assertTrue(accountService.findAllAccountNumbersByUserId(42L).size() > 0);
    }

    @Test
    public void findById() {
        assertNotNull(accountService.findById(48L));
    }
}
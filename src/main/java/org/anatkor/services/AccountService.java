package org.anatkor.services;

import org.anatkor.dao.AccountDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Account;
import org.anatkor.model.enums.Currency;

import java.util.List;

public class AccountService {

    private AccountDao accountDao = new AccountDao();


    public boolean newAccount(String accountName, String currency, Long user_id) {
        Account account = new Account();
        account.setAccountName(accountName);
        account.setCurrency(Currency.valueOf(currency));
        account.setUserId(user_id);
        return accountDao.newAccount(account);
    }

    public List<Account> findAllAccountsByUserIdSorted(Long userId, String sortBy, String order) {
        return accountDao.findAllAccountsByUserIdSorted(userId, sortBy, order);
    }

    public List<Account> findAllAccountsByUserId(Long user_id) {
        return accountDao.findAllAccountsByUserId(user_id);
    }

    public List<Long> findAllAccountNumbersByUserId(Long user_id) {
        return accountDao.findAllAccountNumbersByUserId(user_id);
    }

    public Account findById(Long id) {
        return accountDao.findById(id);
    }

    public Account findAccountByAccountNumber(long accountNumber) throws DBException {
        return accountDao.findByNumber(accountNumber);
    }

    public List<Account> findAllAccountsToDo() {
        return accountDao.findAllAccountsToDo();
    }

    public boolean updateAccountActiveById(Long account_id, Boolean accountActive) {
        return accountDao.updateAccountActiveById(account_id, accountActive);
    }

    public boolean updateAccountActionById(Long account_id, int accountAction) {
        return accountDao.updateAccountActionById(account_id, accountAction);
    }

    public boolean updateAccountBalanceById(Long account_id, int amount) {
        return accountDao.updateAccountBalanceById(account_id, amount);
    }
}

package org.anatkor.services;

import org.anatkor.dao.TransactionDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Account;
import org.anatkor.model.Payment;
import org.anatkor.model.Transaction;
import org.anatkor.model.enums.Currency;

import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao = new TransactionDao();
    private final UserService userService = new UserService();
    private final AccountService accountService = new AccountService();

    public boolean makeTransaction(Transaction transaction) {
        return transactionDao.makeTransaction(transaction);
    }

    public List<Transaction> findAllTransactionsByUserIdSorted(long user_id, String sortBy, String order, String accountType) {
        return transactionDao.findAllTransactionsByUserIdSorted(user_id, sortBy, order, accountType);
    }

    public List<Transaction> findAllTransactionsByAccountNumberSorted(long account_number, String sortBy, String order, String accountType) {
        return transactionDao.findAllTransactionsByAccountNumberSorted(account_number, sortBy, order, accountType);
    }

    public List<Transaction> findAllTransactionsSorted(String sortBy, String order) {
        return transactionDao.findAllTransactionsSorted(sortBy, order);
    }

    public Payment getPayment(Account account, long receiver, String destination, int amount) {
        Payment payment = new Payment();
        payment.setPayer(account.getNumber());
        payment.setReceiver(receiver);
        payment.setPayerAccountName(account.getAccountName());
        payment.setReceiverFullName(userService.findUserFullNameByAccountNumber(receiver));
        payment.setDestination(destination);
        payment.setAmount(amount);
        payment.setCurrency(account.getCurrency());
        return payment;
    }

    public String checkAccount(Account account, int amount) {
        if (account == null || amount <= 0) {
            return "redirect:/payment";
        }
        if (amount > account.getBalance()) {
            return "redirect:wallet/payment?warn=not_enough";
        }
        return "checked";
    }

    public String checkReceiverForActiveAndCurrency(long receiver, Currency currency) {
        Account account;
        try {
            account = accountService.findAccountByAccountNumber(receiver);
        } catch (DBException e) {
            return "redirect:wallet/payment?warn=account_not_found";
        }
        if (!account.getCurrency().equals(currency)) {
            return "redirect:wallet/payment?warn=not_currency&message=" + currency.name();
        }
        if (!account.isActive()) {
            return "redirect:wallet/payment?warn=account_blocked";
        }
        return "checked";
    }
}

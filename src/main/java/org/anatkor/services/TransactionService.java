package org.anatkor.services;

import org.anatkor.dao.TransactionDao;
import org.anatkor.model.Account;
import org.anatkor.model.Payment;
import org.anatkor.model.Transaction;
import org.anatkor.model.enums.Currency;

import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao = new TransactionDao();
    private final UserService userService = new UserService();

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

    public Payment getPayment(Account account, long receiver, String destination, int amount, Currency currency) {
        Payment payment = new Payment();
        payment.setPayer(account.getNumber());
        payment.setReceiver(receiver);
        payment.setPayerAccountName(account.getAccountName());
        payment.setReceiverFullName(userService.findUserFullNameByAccounNumber(receiver));
        if (destination != null) {
            payment.setDestination(destination);
        } else {
            payment.setDestination("-");
        }
        payment.setAmount(amount);
        payment.setCurrency(currency);
        return payment;
    }

}

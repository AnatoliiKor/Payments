package org.anatkor.services;

import org.anatkor.dao.TransactionDao;
import org.anatkor.model.Transaction;

import java.util.List;

public class TransactionService {
    private TransactionDao transactionDao = new TransactionDao();

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


}

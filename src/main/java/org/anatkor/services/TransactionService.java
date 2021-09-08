package org.anatkor.services;

import org.anatkor.dao.PaymentDao;
import org.anatkor.dao.TransactionDao;
import org.anatkor.model.Payment;
import org.anatkor.model.Transaction;

import java.util.List;

public class TransactionService {
    private TransactionDao transactionDao = new TransactionDao();

    public boolean makeTransaction(Transaction transaction) {
        return transactionDao.makeTransaction(transaction);
    }

//    public List<Payment> findAllPaymentsByUserIdSorted(long user_id, String sortBy, String order) {
//        return paymentDao.findAllPaymentsByUserIdSorted(user_id, sortBy, order);
//    }
//
//    public List<Payment> findAllPaymentsByAccountNumberSorted(long account_number, String sortBy, String order) {
//        return paymentDao.findAllPaymentsByAccountNumberSorted(account_number, sortBy, order);
//    }

}

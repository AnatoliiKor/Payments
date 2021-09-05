package org.anatkor.services;

import org.anatkor.dao.PaymentDao;
import org.anatkor.model.Payment;

import java.util.List;

public class PaymentService {
    private PaymentDao paymentDao = new PaymentDao();


    public boolean makePayment(Payment payment) {
        return paymentDao.makePayment(payment);
    }

    public List<Payment> findAllAccountsByUserIdSorted(long user_id, String sortBy, String order) {
        return paymentDao.findAllPaymentsByUserIdSorted(user_id, sortBy, order);
    }

    public List<Payment> findAllAccountsByUserId(long user_id) {
        return paymentDao.findAllPaymentsByUserId(user_id);
    }
}

package org.anatkor.services;

import org.anatkor.dao.PaymentDao;
import org.anatkor.model.Payment;

public class PaymentService {
    private PaymentDao paymentDao = new PaymentDao();


    public boolean makePayment(Payment payment) {
        return paymentDao.makePayment(payment);
    }
}

package org.anatkor.dao;

import org.anatkor.model.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDao {
    private static final String ADD_PAYMENT =
            "INSERT INTO payment (account_number, account_name, receiver, destination, amount, currency) VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE account SET balance=balance-? WHERE number=?";
    final static Logger log = LogManager.getLogger(PaymentDao.class);

    public boolean makePayment(Payment payment) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = con.prepareStatement(UPDATE_ACCOUNT_BALANCE);
            int k = 1;
            preparedStatement.setLong(k++, payment.getAmount());
            preparedStatement.setLong(k++, payment.getAccountNumber());
            if (preparedStatement.executeUpdate() > 0) {
                log.debug("Account {} is updated, wait for commit", payment.getAccountNumber());
                preparedStatement = null;
                preparedStatement = con.prepareStatement(ADD_PAYMENT);
                k = 1;
                preparedStatement.setLong(k++, payment.getAccountNumber());
                preparedStatement.setString(k++, payment.getAccountName());
                preparedStatement.setLong(k++, payment.getReceiver());
                preparedStatement.setString(k++, payment.getDestination());
                preparedStatement.setInt(k++, payment.getAmount());
                preparedStatement.setString(k, payment.getCurrency().name());
                if (preparedStatement.executeUpdate() > 0) {
                    con.commit();
                    log.info("Payment from {} to {}, amount {} is added to DB",
                            payment.getAccountNumber(), payment.getReceiver(), payment.getAmount());
                    return true;
                }
            }
        } catch (SQLException e) {
            log.debug("SQLException during {} processing {}. {}", ADD_PAYMENT, Utils.class, e.getMessage());
            try {
                con.rollback();
            } catch (SQLException throwables) {
                log.debug("SQLException during rollback Add account processing {}. {}", Utils.class, throwables.getMessage());
            }
        } finally {
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return false;
    }
}

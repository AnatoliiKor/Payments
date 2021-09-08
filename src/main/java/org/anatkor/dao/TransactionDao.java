package org.anatkor.dao;

import org.anatkor.model.Payment;
import org.anatkor.model.Transaction;
import org.anatkor.model.enums.Currency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    private static final String ADD_TRANSACTION =
            "INSERT INTO transaction (payer, receiver, destination, amount, currency) VALUES(?,?,?,?,?)";
//    private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE account SET balance=balance-? WHERE number=?";
    final static Logger log = LogManager.getLogger(TransactionDao.class);
    private AccountDao accountDao = new AccountDao();

    public boolean makeTransaction(Transaction transaction) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            if (accountDao.updateBalance(con, transaction.getPayer(), transaction.getAmount())) {
                log.info("Amount {} is deducted from the account {}", transaction.getAmount(), transaction.getPayer());
            } else {
                throw new SQLException();
            }
            if (accountDao.updateBalance(con, transaction.getReceiver(), transaction.getAmount())) {
                log.info("Account {} is refilled with amount {}", transaction.getReceiver(), transaction.getAmount());
            } else {
                throw new SQLException();
            }
                preparedStatement = con.prepareStatement(ADD_TRANSACTION);
                int k = 1;
                preparedStatement.setLong(k++, transaction.getPayer());
                preparedStatement.setLong(k++, transaction.getReceiver());
                preparedStatement.setString(k++, transaction.getDestination());
                preparedStatement.setInt(k++, transaction.getAmount());
                preparedStatement.setString(k, transaction.getCurrency().name());
                if (preparedStatement.executeUpdate() > 0) {
                    con.commit();
                    log.info("Transaction from {} to {}, amount {} is added to DB",
                            transaction.getPayer(), transaction.getReceiver(), transaction.getAmount());
                    return true;
                }
        } catch (SQLException e) {
            log.debug("SQLException during {} processing {}. {}", ADD_TRANSACTION, Utils.class, e.getMessage());
            try {
                con.rollback();
            } catch (SQLException throwables) {
                log.debug("SQLException during rollback {}. {}", Utils.class, throwables.getMessage());
            }
        } finally {
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return false;
    }




    public List<Payment> findAllPaymentsByUserIdSorted(Long id, String sortBy, String order) {
        List<Payment> payments = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sqlId = "";
        String sql;
        if (id > 0) {
            sqlId = " WHERE user_id=" + id;
        }
        sql = "SELECT p.id, account_number, p.account_name, receiver, p.registered, destination, amount, p.currency FROM payment p INNER JOIN account ON account_number=number" + sqlId + " " +
                "ORDER BY " + sortBy + " " + order;
        return getPaymentsSorted(payments, con, statement, rs, sql);
    }

    public List<Payment> findAllPaymentsByAccountNumberSorted(Long number, String sortBy, String order) {
        List<Payment> payments = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM payment WHERE account_number=" + number + " ORDER BY " + sortBy + " " + order;
        return getPaymentsSorted(payments, con, statement, rs, sql);
    }

    private List<Payment> getPaymentsSorted(List<Payment> payments, Connection con, Statement statement, ResultSet rs, String sql) {
        try {
            con = ConnectionPool.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getLong("id"));
                payment.setAccountNumber(rs.getLong("account_number"));
                payment.setAccountName(rs.getString("account_name"));
                payment.setReceiver(rs.getLong("receiver"));
                payment.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
                if (rs.getString("destination") != null) {
                    payment.setDestination(rs.getString("destination"));
                }
                payment.setAmount(rs.getInt("amount"));
                payment.setCurrency(Currency.valueOf(rs.getString("currency")));
                payments.add(payment);
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", sql, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(statement);
            Utils.close(con);
        }
        return payments;
    }
}

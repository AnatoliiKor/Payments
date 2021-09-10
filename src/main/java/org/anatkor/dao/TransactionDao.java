package org.anatkor.dao;

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
    final static Logger log = LogManager.getLogger(TransactionDao.class);
    private AccountDao accountDao = new AccountDao();

    public boolean makeTransaction(Transaction transaction) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            if (accountDao.updateBalance(con, transaction.getPayer(), -transaction.getAmount())) {
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

    public List<Transaction> findAllTransactionsByUserIdSorted(Long id, String sortBy, String order, String accountType) {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sqlId = "";
        String sql;
        if (id > 0) {
            sqlId = " WHERE user_id=" + id;
        }
        sql = "SELECT t.id, payer, receiver, t.registered, destination, amount, t.currency FROM transaction t INNER JOIN account ON " + accountType + "=number" + sqlId + " " +
                "ORDER BY " + sortBy + " " + order;
        return getTransactionsSorted(con, statement, rs, sql);
    }

    public List<Transaction> findAllTransactionsByAccountNumberSorted(Long number, String sortBy, String order, String accountType) {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM transaction WHERE " + accountType + "=" + number + " ORDER BY " + sortBy + " " + order;
        return getTransactionsSorted(con, statement, rs, sql);
    }

    public List<Transaction> findAllTransactionsSorted(String sortBy, String order) {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM transaction ORDER BY " + sortBy + " " + order;
        return getTransactionsSorted(con, statement, rs, sql);
    }

    private List<Transaction> getTransactionsSorted(Connection con, Statement statement, ResultSet rs, String sql) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            con = ConnectionPool.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getLong("id"));
                transaction.setPayer(rs.getLong("payer"));
                transaction.setReceiver(rs.getLong("receiver"));
                transaction.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
                if (rs.getString("destination") != null) {
                    transaction.setDestination(rs.getString("destination"));
                }
                transaction.setAmount(rs.getInt("amount"));
                transaction.setCurrency(Currency.valueOf(rs.getString("currency")));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", sql, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(statement);
            Utils.close(con);
        }
        return transactions;
    }
}

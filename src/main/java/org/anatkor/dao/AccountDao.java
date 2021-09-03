package org.anatkor.dao;

import org.anatkor.model.Account;
import org.anatkor.model.enums.Currency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private static final String FIND_MAX_ACCOUNT_NUMBER = "SELECT MAX(number) FROM account";
    private static final String ADD_ACCOUNT = "INSERT INTO account (number, balance, account_name, currency, user_id, action) VALUES (?,0,?,?,?,1);";
    private static final String FIND_ACCOUNTS_BY_USER_ID_SORTED = "SELECT * FROM account WHERE user_id=? ORDER BY ? ?";
    private static final String FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id=?";
    private static final String FIND_ACCOUNT_WITH_CARD_BY_ID = "SELECT * FROM account LEFT JOIN credit_card cc on account.id = cc.account_id where account.id=?";
    private static final String FIND_ALL_ACCOUNTS_TO_DO = "SELECT * FROM account WHERE action>0";
    private static final String UPDATE_ACCOUNT_ACTIVE_BY_ID = "UPDATE account SET active=?, action=0 WHERE id=?";
    private static final String UPDATE_ACCOUNT_BALANCE_BY_ID = "UPDATE account SET balance=balance+? WHERE id=?";
    private static final String UPDATE_ACCOUNT_ACTION_BY_ID = "UPDATE account SET action=? WHERE id=?";
    private static final String ADD_CREDIT_CARD_ACCOUNT = "INSERT INTO credit_card(account_id) VALUES (?)";


    final static Logger log = LogManager.getLogger(AccountDao.class);

    public boolean newAccount(Account account) {
        Connection con = null;
        Statement statement;
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        long generatedId;
        boolean result;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = con.createStatement();
            rs = statement.executeQuery(FIND_MAX_ACCOUNT_NUMBER);
            if (rs.next()) {
                long number = rs.getLong("max");
                rs=null;
                number++;
                prepStatement = con.prepareStatement(ADD_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
                int k = 1;
                prepStatement.setLong(k++, number);
                prepStatement.setString(k++, account.getAccountName());
                prepStatement.setString(k++, String.valueOf(account.getCurrency()));
                prepStatement.setLong(k, account.getUserId());
                if (prepStatement.executeUpdate() > 0) {
                    log.info("Account {} is added to DB", number);
                    rs = prepStatement.getGeneratedKeys();
                    if (rs.next()) {
                        generatedId = rs.getLong(1);
                        account.setId(generatedId);
                        prepStatement=null;
                        prepStatement = con.prepareStatement(ADD_CREDIT_CARD_ACCOUNT);
                        prepStatement.setLong(1, generatedId);
                        result = (1 == prepStatement.executeUpdate());
                        con.commit();
                        log.info("Credit card is added to account id={}", account.getId());
                        return result;
                    }
                }
            }
        } catch (SQLException e) {
            log.debug("SQLException during Add account processing {}. {}", Utils.class, e.getMessage());
            try {
                con.rollback();
            } catch (SQLException throwables) {
                log.debug("SQLException during rollback Add account processing {}. {}", Utils.class, throwables.getMessage());
            }
        }
        finally {
            Utils.close(rs);
            Utils.close(prepStatement);
            Utils.close(con);
        }
        return false;
    }

    public List<Account> findAllAccountsByUserIdSorted(Long id, String sortBy, String order) {
        List<Account> accounts = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sqlId = "";
        String sql;
        try {
            con = ConnectionPool.getConnection();
            if (id>0) {
                sqlId = " WHERE user_id=" + id;
            }
            sql = "SELECT * FROM account" + sqlId + " ORDER BY " + sortBy + " " + order;
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setNumber(rs.getLong("number"));
                account.setBalance(rs.getLong("balance"));
                account.setAccountName(rs.getString("account_name"));
                account.setCurrency(Currency.valueOf(rs.getString("currency")));
                account.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
                account.setActive(rs.getBoolean("active"));
                account.setUserId(rs.getLong("user_id"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", FIND_ACCOUNTS_BY_USER_ID_SORTED, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(statement);
            Utils.close(con);
        }
        return accounts;
    }

    public List<Account> findAllAccountsByUserId(Long id) {
        List<Account> accounts = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sql;
        try {
            con = ConnectionPool.getConnection();
            sql = "SELECT * FROM account LEFT JOIN credit_card cc on account.id = cc.account_id WHERE user_id=" + id;
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setNumber(rs.getLong("number"));
                account.setBalance(rs.getLong("balance"));
                account.setAccountName(rs.getString("account_name"));
                account.setCurrency(Currency.valueOf(rs.getString("currency")));
                account.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
                account.setActive(rs.getBoolean("active"));
                account.setCardNumber(rs.getLong("card_id"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", FIND_ACCOUNTS_BY_USER_ID_SORTED, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(statement);
            Utils.close(con);
        }
        return accounts;
    }

    public Account findById(Long id) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            preparedStatement = con.prepareStatement(FIND_ACCOUNT_WITH_CARD_BY_ID);
            int k = 1;
            preparedStatement.setLong(k, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setNumber(rs.getLong("number"));
                account.setBalance(rs.getLong("balance"));
                account.setAccountName(rs.getString("account_name"));
                account.setCurrency(Currency.valueOf(rs.getString("currency")));
                account.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
                account.setActive(rs.getBoolean("active"));
                account.setAction(rs.getInt("action"));
                account.setUserId(rs.getLong("user_id"));
                account.setCardNumber(rs.getLong("card_id"));
                return account;
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", FIND_ACCOUNT_BY_ID, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return null;
    }

    public List<Account> findAllAccountsToDo() {
        Connection con = null;
        Statement statement=null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(FIND_ALL_ACCOUNTS_TO_DO);
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setNumber(rs.getLong("number"));
                account.setBalance(rs.getLong("balance"));
                account.setAccountName(rs.getString("account_name"));
                account.setCurrency(Currency.valueOf(rs.getString("currency")));
                account.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
                account.setActive(rs.getBoolean("active"));
                account.setAction(rs.getInt("action"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", FIND_ALL_ACCOUNTS_TO_DO, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(statement);
            Utils.close(con);
        }
        return null;
    }

    public boolean updateAccountBalanceById(Long account_id, int amount) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionPool.getConnection();
            preparedStatement = con.prepareStatement(UPDATE_ACCOUNT_BALANCE_BY_ID);
            int k = 1;
            preparedStatement.setInt(k++, amount);
            preparedStatement.setLong(k, account_id);
            if (preparedStatement.executeUpdate() > 0) {
                log.info("Account (id={}) is replenished with {}", account_id, amount);
                return true;
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}. {}",
                    UPDATE_ACCOUNT_BALANCE_BY_ID, Utils.class, e.getMessage());
        } finally {
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return false;
    }

    public boolean updateAccountActiveById(Long account_id, Boolean accountActive) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionPool.getConnection();
            preparedStatement = con.prepareStatement(UPDATE_ACCOUNT_ACTIVE_BY_ID);
            int k = 1;
            preparedStatement.setBoolean(k++, accountActive);
            preparedStatement.setLong(k, account_id);
            if (preparedStatement.executeUpdate() > 0) {
                log.info("Status of account with id={} is updated to {}", account_id, accountActive);
                return true;
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}. {}",
                    UPDATE_ACCOUNT_ACTIVE_BY_ID, Utils.class, e.getMessage());
        } finally {
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return false;
    }

    public boolean updateAccountActionById(Long account_id, int accountAction) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionPool.getConnection();
            preparedStatement = con.prepareStatement(UPDATE_ACCOUNT_ACTION_BY_ID);
            int k = 1;
            preparedStatement.setInt(k++, accountAction);
            preparedStatement.setLong(k, account_id);
            if (preparedStatement.executeUpdate() > 0) {
                log.info("Action flag of account with id={} is updated to {}", account_id, accountAction);
                return true;
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}. {}",
                    UPDATE_ACCOUNT_ACTION_BY_ID, Utils.class, e.getMessage());
        } finally {
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return false;
    }
}

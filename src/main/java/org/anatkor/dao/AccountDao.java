package org.anatkor.dao;

import org.anatkor.constants.Query;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Account;
import org.anatkor.model.enums.Currency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
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
            rs = statement.executeQuery(Query.FIND_MAX_ACCOUNT_NUMBER);
            if (rs.next()) {
                long number = rs.getLong("max");
                rs = null;
                number++;
                prepStatement = con.prepareStatement(Query.ADD_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
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
                        prepStatement = null;
                        prepStatement = con.prepareStatement(Query.ADD_CREDIT_CARD_ACCOUNT);
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
        } finally {
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
        if (id > 0) {
            sqlId = " WHERE user_id=" + id;
        }
        String sql = "SELECT * FROM account" + sqlId + " ORDER BY " + sortBy + " " + order;

        try {
            con = ConnectionPool.getConnection();
            assert con != null;
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", Query.FIND_ACCOUNTS_BY_USER_ID_SORTED, Utils.class, e);
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
                Account account = getAccountFromResultSet(rs);
                account.setCardNumber(rs.getLong("card_id"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", Query.FIND_ACCOUNTS_BY_USER_ID_SORTED, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(statement);
            Utils.close(con);
        }
        return accounts;
    }

    public List<Long> findAllAccountNumbersByUserId(Long id) {
        List<Long> numbers = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT number FROM account WHERE user_id=" + id;
        try {
            con = ConnectionPool.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                long number = rs.getLong("number");
                numbers.add(number);
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", sql, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(statement);
            Utils.close(con);
        }
        return numbers;
    }


    public Account findById(Long id) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            preparedStatement = con.prepareStatement(Query.FIND_ACCOUNT_WITH_CARD_BY_ID);
            int k = 1;
            preparedStatement.setLong(k, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Account account = getAccountFromResultSet(rs);
                account.setCardNumber(rs.getLong("card_id"));
                return account;
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", Query.FIND_ACCOUNT_BY_ID, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return null;
    }

    public Account findByNumber(Long number) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            preparedStatement = con.prepareStatement(Query.FIND_ACCOUNT_WITH_CARD_BY_NUMBER);
            int k = 1;
            preparedStatement.setLong(k, number);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Account account = getAccountFromResultSet(rs);
                account.setCardNumber(rs.getLong("card_id"));
                return account;
            } else {
                log.info("Account {} not found", number);
                throw new DBException("account not found");
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", Query.FIND_ACCOUNT_WITH_CARD_BY_NUMBER, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return null;
    }

    public List<Account> findAllAccountsToDo() {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(Query.FIND_ALL_ACCOUNTS_TO_DO);
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
            return accounts;
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", Query.FIND_ALL_ACCOUNTS_TO_DO, Utils.class, e);
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
            preparedStatement = con.prepareStatement(Query.UPDATE_ACCOUNT_BALANCE_BY_ID);
            int k = 1;
            preparedStatement.setInt(k++, amount);
            preparedStatement.setLong(k, account_id);
            if (preparedStatement.executeUpdate() > 0) {
                log.info("Account (id={}) is replenished with {}", account_id, amount);
                return true;
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}. {}",
                    Query.UPDATE_ACCOUNT_BALANCE_BY_ID, Utils.class, e.getMessage());
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
            preparedStatement = con.prepareStatement(Query.UPDATE_ACCOUNT_ACTIVE_BY_ID);
            int k = 1;
            preparedStatement.setBoolean(k++, accountActive);
            preparedStatement.setLong(k, account_id);
            if (preparedStatement.executeUpdate() > 0) {
                log.info("Status of account with id={} is updated to {}", account_id, accountActive);
                return true;
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}. {}",
                    Query.UPDATE_ACCOUNT_ACTIVE_BY_ID, Utils.class, e.getMessage());
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
            preparedStatement = con.prepareStatement(Query.UPDATE_ACCOUNT_ACTION_BY_ID);
            int k = 1;
            preparedStatement.setInt(k++, accountAction);
            preparedStatement.setLong(k, account_id);
            if (preparedStatement.executeUpdate() > 0) {
                log.info("Action flag of account with id={} is updated to {}", account_id, accountAction);
                return true;
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}. {}",
                    Query.UPDATE_ACCOUNT_ACTION_BY_ID, Utils.class, e.getMessage());
        } finally {
            Utils.close(preparedStatement);
            Utils.close(con);
        }
        return false;
    }


    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
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
        return account;
    }

    public boolean updateBalance(Connection con, Long account_number, int amount) throws SQLException {
        PreparedStatement prepStatement = null;
        try {
            prepStatement = con.prepareStatement(Query.UPDATE_ACCOUNT_BALANCE);
            int k = 1;
            prepStatement.setInt(k++, amount);
            prepStatement.setLong(k, account_number);
            if (prepStatement.executeUpdate() > 0) {
                log.info("Account {} is updated with {}", account_number, amount);
                return true;
            }
        } finally {
            Utils.close(prepStatement);
        }
        return false;
    }
}

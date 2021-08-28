package org.anatkor.dao;

import org.anatkor.model.Account;
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
    private static final String FIND_ALL_ACCOUNTS_TO_DO = "SELECT * FROM account WHERE action>0";
    private static final String UPDATE_ACCOUNT_ACTIVE_BY_ID = "UPDATE account SET active=?, action=0 WHERE id=?";
    private static final String UPDATE_ACCOUNT_BALANCE_BY_ID = "UPDATE account SET balance=balance+? WHERE id=?";
    private static final String UPDATE_ACCOUNT_ACTION_BY_ID = "UPDATE account SET action=? WHERE id=?";

    private static final String FIND_ALL_BIKES_SORTED = "SELECT * FROM bike ORDER BY ";

    private static final String FIND_BIKE_BY_ID = "SELECT * FROM bike WHERE id=?";
    private static final String DELETE_BIKE_BY_ID = "DELETE FROM bike WHERE id=?";
    private static final String ADD_BIKE = "INSERT INTO bike (name, brand, category, colour, description, price)" +
            " VALUES (?,?,?,?,?,?)";


    final static Logger log = LogManager.getLogger(AccountDao.class);

    public boolean newAccount(Account account) {
        Connection con = null;
        Statement statement;
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            con = Utils.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(FIND_MAX_ACCOUNT_NUMBER);
            if (rs.next()) {
                long number = rs.getLong("max");
                number++;
                prepStatement = con.prepareStatement(ADD_ACCOUNT);
                int k = 1;
                prepStatement.setLong(k++, number);
                prepStatement.setString(k++, account.getAccountName());
                prepStatement.setString(k++, String.valueOf(account.getCurrency()));
                prepStatement.setLong(k++, account.getUserId());
                if (prepStatement.executeUpdate() > 0) {
                    log.info("Account {} is added to DB", number);
                    return true;
                }
            }
        } catch (SQLException e) {
            log.info("SQLException during Add account processing {}. {}", Utils.class, e.getMessage());
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
            Utils.close(con);
        }
        return false;
    }

    public List<Account> findAllAccountsByUserId(Long id, String sortBy, String order) {
        List<Account> accounts = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        String sqlId = "";
        String sql;
        try {
            con = Utils.getConnection();
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
                account.setCurrency(Account.CURRENCY.valueOf(rs.getString("currency")));
                account.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
                account.setActive(rs.getBoolean("active"));
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
            con = Utils.getConnection();
            preparedStatement = con.prepareStatement(FIND_ACCOUNT_BY_ID);
            int k = 1;
            preparedStatement.setLong(k, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setNumber(rs.getLong("number"));
                account.setBalance(rs.getLong("balance"));
                account.setAccountName(rs.getString("account_name"));
                account.setCurrency(Account.CURRENCY.valueOf(rs.getString("currency")));
                account.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
                account.setActive(rs.getBoolean("active"));
                account.setAction(rs.getInt("action"));
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
            con = Utils.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery(FIND_ALL_ACCOUNTS_TO_DO);
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setNumber(rs.getLong("number"));
                account.setBalance(rs.getLong("balance"));
                account.setAccountName(rs.getString("account_name"));
                account.setCurrency(Account.CURRENCY.valueOf(rs.getString("currency")));
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
            con = Utils.getConnection();
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
            con = Utils.getConnection();
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
            con = Utils.getConnection();
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



//
//
//
//    public List<Bike> findAll(String sortBy, String order) {
//        List<Bike> bikes = new ArrayList<>();
//        Connection con = null;
//        Statement statement = null;
//        ResultSet rs = null;
//        try {
//            con = Utils.getConnection();
//            statement = con.createStatement();
//            String sql = FIND_ALL_BIKES_SORTED + sortBy + " " + order;
//            rs = statement.executeQuery(FIND_ALL_BIKES_SORTED + sortBy + " " + order);
//            while (rs.next()) {
//                long id = rs.getLong("id");
//                String name = rs.getString("name");
//                String brand = rs.getString("brand");
//                String category = rs.getString("category");
//                String colour = rs.getString("colour");
//                String description = rs.getString("description");
//                int price = rs.getInt("price");
//                LocalDateTime registrationDateTime = rs.getTimestamp("date").toLocalDateTime();
//
//                Bike bike = new Bike.Builder()
//                        .withId(id)
//                        .withName(name)
//                        .withBrand(brand)
//                        .withCategory(category)
//                        .withColour(colour)
//                        .withDescription(description)
//                        .withPrice(price)
//                        .withRegistrationDateTime(registrationDateTime)
//                        .build();
//                bikes.add(bike);
//            }
//        } catch (SQLException e) {
//            log.debug("SQLException during Query {} processing from {}.", FIND_ALL_BIKES_SORTED, Utils.class, e);
//        } finally {
//            Utils.close(rs);
//            Utils.close(statement);
//            Utils.close(con);
//        }
//        return bikes;
//    }
//
//    public boolean newBike(Bike bike) throws DBException {
//        boolean result = false;
//        Connection con = null;
//        PreparedStatement prepStatement = null;
//        ResultSet rs = null;
//        try {
//            con = Utils.getConnection();
//            if (bike.getId() < 0) {
//                prepStatement = con.prepareStatement(ADD_BIKE, Statement.RETURN_GENERATED_KEYS);
//            } else {
//                prepStatement = con.prepareStatement(UPDATE_BIKE);
//                prepStatement.setLong(7, bike.getId());
//            }
//            int k = 1;
//            prepStatement.setString(k++, bike.getName());
//            prepStatement.setString(k++, bike.getBrand());
//            prepStatement.setString(k++, bike.getCategory());
//            prepStatement.setString(k++, bike.getColour());
//            prepStatement.setString(k++, bike.getDescription());
//            prepStatement.setInt(k++, bike.getPrice());
//            if (prepStatement.executeUpdate() > 0) {
//                result = true;
//                log.info("Bike {} is processed", bike.getName());
//                result = true;
//                rs = prepStatement.getGeneratedKeys();
//                long generatedId;
////                if (rs.next()) {
////                    generatedId = rs.getLong(1);
////                    bike.setId(generatedId);
////                }
//            }
//        } catch (SQLException e) {
//            log.info("SQLException during Add {} processing {}. {}", bike.getName(), Utils.class, e.getMessage());
//            throw new DBException("Bike is not added to the DB", e);
//        } finally {
//            Utils.close(rs);
//            Utils.close(prepStatement);
//            Utils.close(con);
//        }
//        return result;
//    }
//
//    public Bike findBikeById(Long bikeId) {
//        boolean result = false;
//        Connection con = null;
//        PreparedStatement prepStatement = null;
//        ResultSet rs = null;
//        try {
//            con = Utils.getConnection();
//            prepStatement = con.prepareStatement(FIND_BIKE_BY_ID);
//            prepStatement.setLong(1, bikeId);
//            rs = prepStatement.executeQuery();
//            if (rs.next()) {
//                long id = rs.getLong("id");
//                String name = rs.getString("name");
//                String brand = rs.getString("brand");
//                String category = rs.getString("category");
//                String colour = rs.getString("colour");
//                String description = rs.getString("description");
//                int price = rs.getInt("price");
//                LocalDateTime registrationDateTime = rs.getTimestamp("date").toLocalDateTime();
//                Bike bike = new Bike.Builder()
//                        .withId(id)
//                        .withName(name)
//                        .withBrand(brand)
//                        .withCategory(category)
//                        .withColour(colour)
//                        .withDescription(description)
//                        .withPrice(price)
//                        .withRegistrationDateTime(registrationDateTime)
//                        .build();
//                log.info("Bike id = {} is taken from DB", bike.getId());
//                return bike;
//            }
//
//        } catch (SQLException e) {
//            log.info("SQLException during FindBikeById {} processing {}. {}", bikeId, Utils.class, e.getMessage());
////            throw new DBException("Bike is not added to the DB", e);
//        } finally {
//            Utils.close(rs);
//            Utils.close(prepStatement);
//            Utils.close(con);
//        }
//        return null;
//    }
//
//    public boolean deleteBike(Long id) throws DBException {
//        Connection con = null;
//        PreparedStatement prepStatement = null;
//        boolean result = false;
//        try {
//            con = Utils.getConnection();
//            prepStatement = con.prepareStatement(DELETE_BIKE_BY_ID);
//            prepStatement.setLong(1, id);
//            if (prepStatement.executeUpdate() > 0) {
//                log.info("Bike with id={} is deleted", id);
//                return true;
//            }
//        } catch (SQLException e) {
//            log.debug("SQLException during Query {} processing from {}.", DELETE_BIKE_BY_ID, Utils.class, e);
//            throw new DBException("Cannot delete a bike with id:" + id, e);
//        } finally {
//            Utils.close(prepStatement);
//            Utils.close(con);
//        }
//        return result;
//    }
}

package org.anatkor.dao;

import org.anatkor.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private static final String FIND_MAX_ACCOUNT_NUMBER = "SELECT MAX(number) FROM account";
    private static final String ADD_ACCOUNT = "INSERT INTO account (number, balance, account_name, currency, user_id) VALUES (?,0,?,?,?);";
    private static final String FIND_ACCOUNTS_BY_USER_ID_SORTED = "SELECT * FROM account WHERE user_id=? ORDER BY ? ?";


    private static final String FIND_ALL_BIKES_SORTED = "SELECT * FROM bike ORDER BY ";

    private static final String FIND_BIKE_BY_ID = "SELECT * FROM bike WHERE id=?";
    private static final String DELETE_BIKE_BY_ID = "DELETE FROM bike WHERE id=?";
    private static final String ADD_BIKE = "INSERT INTO bike (name, brand, category, colour, description, price)" +
            " VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_BIKE = "UPDATE bike SET name = ?, brand = ?, category = ?, colour = ?, description = ?, price = ?" +
            " WHERE id = ?";

    final static Logger log = LogManager.getLogger(AccountDao.class);

    public boolean NewAccount (Account account) {
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

    public List<Account> findAllByUserId(Long userId, String sortBy, String order) {
        List<Account> accounts = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
//        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = Utils.getConnection();
//            statement = con.createStatement();
            String sql = "SELECT * FROM account WHERE user_id=" + userId + " ORDER BY " + sortBy + " " + order;
//            preparedStatement = con.prepareStatement(FIND_ACCOUNTS_BY_USER_ID_SORTED);
//            preparedStatement = con.prepareStatement(FIND_ACCOUNTS_BY_USER_ID_SORTED);
//            int k=1;
//            preparedStatement.setLong(k++, userId);
//            preparedStatement.setString(k++, sortBy);
//            preparedStatement.setString(k, order);
//            String sql = FIND_ACCOUNTS_BY_USER_ID_SORTED + sortBy + " " + order;
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
//            preparedStatement.executeQuery();
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

//                long id = rs.getLong("id");
//                long number = rs.getLong("number");
//                long balance = rs.getLong("balance");
//                String accountName = rs.getString("account_name");
//                Account.CURRENCY currency = Account.CURRENCY.valueOf(rs.getString("currency"));
//                LocalDateTime registrationDateTime = rs.getTimestamp("date").toLocalDateTime();
//                Boolean state = rs.getBoolean("active");
//                CreditCard creditCard = ;

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



//
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

package org.anatkor.dao;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.enums.Role;
import org.anatkor.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String FIND_ALL_USERS = "SELECT * FROM usr;";
    private static final String ADD_USER = "INSERT INTO usr (last_name, name, middle_name, password, email, phone_number) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_USER_STATUS = "UPDATE usr SET active=? WHERE id=?";
    private static final String ADD_USER_ROLE = "INSERT INTO user_role VALUES (?,?)";
    private static final String FIND_USER_BY_PHONE_NUMBER = "SELECT * FROM usr WHERE phone_number=?";
    private static final String FIND_USER_BY_PHONE_NUMBER_AND_PASSWORD = "SELECT * FROM usr WHERE phone_number=? AND password=?;";
    private static final String FIND_ROLE_BY_USERID = "SELECT * FROM user_role WHERE user_id=?";
    private static final String ADD_USER_AND_ROLE = "INSERT INTO usr (username, email, password)  VALUES (?,?,?); INSERT INTO user_role(user_id, role) VALUES ((SELECT id FROM usr WHERE login ='?'), 'USER')";
    private static final String FIND_ALL_USERS_AND_ROLES = "SELECT * From usr LEFT JOIN user_role ON usr.id = user_role.user_id;";
    private static final String FIND_USER_BY_ID = "SELECT * FROM usr WHERE id=?";

    final static Logger log = LogManager.getLogger(UserDao.class);

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            stm = con.createStatement();
            rs = stm.executeQuery(FIND_ALL_USERS);
            while (rs.next()) {
                long id = rs.getLong("id");
                String lastName = rs.getString("last_name");
                String name = rs.getString("name");
                String middleName = rs.getString("middle_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                long phoneNumber = rs.getLong("phone_number");
                LocalDateTime registrationDateTime = rs.getTimestamp("registered").toLocalDateTime();
                boolean active = rs.getBoolean("active");
                Role role = findRoleByUserId(con, id);
                User user = new User.UserBuilder()
                        .withId(id)
                        .withLastName(lastName)
                        .withName(name)
                        .withMiddleName(middleName)
                        .withPassword(password)
                        .withEmail(email)
                        .withPhoneNumber(phoneNumber)
                        .withRegistrationDateTime(registrationDateTime)
                        .withActive(active)
                        .withRole(role)
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", FIND_ALL_USERS, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(stm);
            Utils.close(con);
        }
        return users;
    }

    public User findUserById(Long userId) throws DBException {
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            prepStatement = con.prepareStatement(FIND_USER_BY_ID);
            int k = 1;
            prepStatement.setLong(k, userId);
            rs = prepStatement.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                String lastName = rs.getString("last_name");
                String name = rs.getString("name");
                String middleName = rs.getString("middle_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                long phoneNumber = rs.getLong("phone_number");
                LocalDateTime registrationDateTime = rs.getTimestamp("registered").toLocalDateTime();
                boolean active = rs.getBoolean("active");
                Role role = findRoleByUserId(con, id);
                return new User.UserBuilder()
                        .withId(id)
                        .withLastName(lastName)
                        .withName(name)
                        .withMiddleName(middleName)
                        .withPassword(password)
                        .withEmail(email)
                        .withPhoneNumber(phoneNumber)
                        .withRegistrationDateTime(registrationDateTime)
                        .withActive(active)
                        .withRole(role)
                        .build();
            } else {
                throw new DBException("user_not_found");
            }
        } catch (SQLException e) {
            log.info("SQLException during Query {} processing from {}.", FIND_USER_BY_ID, Utils.class, e);
            throw new DBException("user_not_found");
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
            Utils.close(con);
        }
    }

    public User findUserByPhoneAndPassword(Long phoneNumber, String password) throws DBException {
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            prepStatement = con.prepareStatement(FIND_USER_BY_PHONE_NUMBER_AND_PASSWORD);
            int k = 1;
            prepStatement.setLong(k++, phoneNumber);
            prepStatement.setString(k, password);
            rs = prepStatement.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                String lastName = rs.getString("last_name");
                String name = rs.getString("name");
                String middleName = rs.getString("middle_name");
                password = rs.getString("password");
                String email = rs.getString("email");
                phoneNumber = rs.getLong("phone_number");
                LocalDateTime registrationDateTime = rs.getTimestamp("registered").toLocalDateTime();
                boolean active = rs.getBoolean("active");
                if (!active) {
                    log.info("Attempt to log in. User with phone number \"+" + phoneNumber + "\" is not active.");
                    throw new DBException("user_not_active");
                }
                Role role = findRoleByUserId(con, id);
                return new User.UserBuilder()
                        .withId(id)
                        .withLastName(lastName)
                        .withName(name)
                        .withMiddleName(middleName)
                        .withPassword(password)
                        .withEmail(email)
                        .withPhoneNumber(phoneNumber)
                        .withRegistrationDateTime(registrationDateTime)
                        .withActive(active)
                        .withRole(role)
                        .build();
            } else {
                log.info("User with phone number \"+" + phoneNumber + "\" is not found");
                throw new DBException("user_not_found_check");
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.",
                    FIND_USER_BY_PHONE_NUMBER, Utils.class, e);
            throw new DBException("user_not_found");
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
            Utils.close(con);
        }
    }

    private Role findRoleByUserId(Connection con, Long user_id) throws SQLException {
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            prepStatement = con.prepareStatement(FIND_ROLE_BY_USERID);
            int k = 1;
            prepStatement.setLong(k, user_id);
            rs = prepStatement.executeQuery();
            if (rs.next()) {
                return Role.valueOf(rs.getString("role"));
            }
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
        }
        return null;
    }


    public boolean addUser(User user) throws DBException {
//        Add bike with order look video 2:29
        boolean result;
        Connection con;
        PreparedStatement prepStatement;
        ResultSet rs = null;
        long generatedId = 0L;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            prepStatement = con.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            prepStatement.setString(k++, user.getLastName());
            prepStatement.setString(k++, user.getName());
            prepStatement.setString(k++, user.getMiddleName());
            prepStatement.setString(k++, user.getPassword());
            prepStatement.setString(k++, user.getEmail());
            prepStatement.setLong(k, user.getPhoneNumber());
            if (prepStatement.executeUpdate() > 0) {
                log.info("User with phone ={} is added", user.getPhoneNumber());
                rs = prepStatement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getLong(1);
                    user.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            String exeption = e.getMessage();
            log.debug("SQLException during Add user with phone {} processing {}. {}",
                    user.getPhoneNumber(), Utils.class, e.getMessage());
            if (exeption.contains("mail")) {
                throw new DBException("registration_email_exist");
            } else if (exeption.contains("phone")) {
                throw new DBException("registration_phone_exist");
            } else {
                throw new DBException("not_registered");
            }
        }
        try {
            prepStatement = null;
            prepStatement = con.prepareStatement(ADD_USER_ROLE);
            prepStatement.setLong(1, generatedId);
            prepStatement.setString(2, "CLIENT");
            result = (1 == prepStatement.executeUpdate());
            con.commit();
            log.info("User with id {} with role  is added", user.getId());
            return result;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException throwables) {
                log.debug("SQLException during rollback add user_id={} processing {}. {}",
                        user.getId(), Utils.class, throwables.getMessage());
                throw new DBException("not_registered");
            }
            log.debug("SQLException during Add user_id={} processing {}. {}",
                    user.getId(), Utils.class, e.getMessage());
            throw new DBException("not_registered");
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
            Utils.close(con);
        }
    }

    public boolean updateUserStatus(User user) {
        Connection con = null;
        PreparedStatement prepStatement = null;
        try {
            con = ConnectionPool.getConnection();
            prepStatement = con.prepareStatement(UPDATE_USER_STATUS);
            int k = 1;
            prepStatement.setBoolean(k++, user.isActive());
            prepStatement.setLong(k, user.getId());
            if (prepStatement.executeUpdate() > 0) {
                log.info("Status of user with phone={} is updated to {}", user.getPhoneNumber(), user.isActive());
                return true;
            }
        } catch (SQLException e) {
            log.debug("SQLException during update status of user with phone {} processing {}. {}",
                    user.getPhoneNumber(), Utils.class, e.getMessage());
        } finally {
            Utils.close(prepStatement);
            Utils.close(con);
        }
        return false;
    }
}

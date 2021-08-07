package org.anatkor.dao;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.Role;
import org.anatkor.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDao {
    private static final String FIND_ALL_USERS = "SELECT * FROM usr;";
    private static final String ADD_USER = "INSERT INTO usr (username, email, password) VALUES (?,?,?)";
    private static final String ADD_USER_ROLE = "INSERT INTO user_role VALUES (?,?)";
    private static final String FIND_USER_BY_USERNAME = "SELECT * FROM usr WHERE username=?";
    private static final String FIND_ROLES_BY_USERID = "SELECT * FROM user_role WHERE user_id=?";
    private static final String ADD_USER_AND_ROLE = "INSERT INTO usr (username, email, password)  VALUES (?,?,?); INSERT INTO user_role(user_id, role) VALUES ((SELECT id FROM usr WHERE login ='?'), 'USER')";
    private static final String FIND_ALL_USERS_AND_ROLES = "SELECT * From usr LEFT JOIN user_role ON usr.id = user_role.user_id;";

    final static Logger log = LogManager.getLogger(UserDao.class);

//    public List<User> findAll() {
//        List<User> users = new ArrayList<>();
//        Connection con = null;
//        Statement stm = null;
//        ResultSet rs = null;
////       Set ids is necessary to check for users with several roles
//        Set<Long> ids = new HashSet<>();
//        try {
//            con = Utils.getConnection();
//            stm = con.createStatement();
//            rs = stm.executeQuery(FIND_ALL_USERS_AND_ROLES);
//            while (rs.next()) {
//
//                long id = rs.getLong("id");
//                String role = rs.getString("role");
//
//                if (ids.add(id)) {
//                    String username = rs.getString("username");
//                    String email = rs.getString("email");
//                    String password = rs.getString("password");
//                    LocalDateTime registrationDateTime = rs.getTimestamp("registered").toLocalDateTime();
//                    boolean active = rs.getBoolean("active");
//                    User user = new User.UserBuilder()
//                            .withId(id)
//                            .withPassword(password)
//                            .withUsername(username)
//                            .withEmail(email)
//                            .withRegistrationDateTime(registrationDateTime)
//                            .withActive(active)
//                            .withRoles(role)
//                            .build();
//                    users.add(user);
//                } else {
//                    for (User u : users) {
//                        if (u.getId() == id) {
//                            u.addRole(role);
//                            users.set(users.indexOf(u), u);
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            log.debug("SQLException during Query {} processing from {}.", FIND_ALL_USERS_AND_ROLES, Utils.class, e);
//        } finally {
//            Utils.close(rs);
//            Utils.close(stm);
//            Utils.close(con);
//        }
//        return users;
//    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = Utils.getConnection();
            stm = con.createStatement();
            rs = stm.executeQuery(FIND_ALL_USERS);
            while (rs.next()) {
                    Long id = rs.getLong("id");
                    Set<Role> roles = findRolesByUsername(con, id);
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    LocalDateTime registrationDateTime = rs.getTimestamp("registered").toLocalDateTime();
                    boolean active = rs.getBoolean("active");
                    User user = new User.UserBuilder()
                            .withId(id)
                            .withPassword(password)
                            .withUsername(username)
                            .withEmail(email)
                            .withRegistrationDateTime(registrationDateTime)
                            .withActive(active)
                            .withRoles(roles)
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

    public User findUserByUsername(String username) throws DBException{
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            con = Utils.getConnection();
            prepStatement = con.prepareStatement(FIND_USER_BY_USERNAME);
            int k = 1;
            prepStatement.setString(k++, username);
            rs = prepStatement.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                Set<Role> roles = findRolesByUsername(con, id);
                username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                LocalDateTime registrationDateTime = rs.getTimestamp("registered").toLocalDateTime();
                boolean active = rs.getBoolean("active");
                User user = new User.UserBuilder()
                        .withId(id)
                        .withPassword(password)
                        .withUsername(username)
                        .withEmail(email)
                        .withRegistrationDateTime(registrationDateTime)
                        .withActive(active)
                        .withRoles(roles)
                        .build();
                return user;
            } else {throw new DBException("User with \"" + username + "\" is not found");}
        } catch (SQLException e) {
            log.info("SQLException during Query {} processing from {}.", FIND_USER_BY_USERNAME, Utils.class, e);
            throw new DBException("User with \"" + username + " \" is not found");
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
            Utils.close(con);
        }
    }

    private Set<Role> findRolesByUsername(Connection con, Long user_id) throws SQLException {
        Set<Role> roles = new HashSet<>();
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            prepStatement = con.prepareStatement(FIND_ROLES_BY_USERID);
            int k = 1;
            prepStatement.setLong(k++, user_id);
            rs = prepStatement.executeQuery();
            while (rs.next()) {
                Role role = Role.valueOf(rs.getString("role"));
                roles.add(role);
            }
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
        }
        return roles;
    }


    public void addUser(User user) throws DBException, SQLException {
//        Add bike with order look video 2:29
        boolean result = false;
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        long generatedId = 0L;
        try {
            con = Utils.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//            prepStatement = con.prepareStatement(ADD_USER, new String[]{"id"});
            prepStatement = con.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            prepStatement.setString(k++, user.getUsername());
            prepStatement.setString(k++, user.getEmail());
            prepStatement.setString(k++, user.getPassword());
            if (prepStatement.executeUpdate() > 0) {
                log.info("User {} is added", user.getUsername());
                result = true;
                rs = prepStatement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getLong(1);
                    user.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            log.info("SQLException during Add {} processing {}. {}", user.getUsername(), Utils.class, e.getMessage());
            throw new DBException("User is not added to the DB", e);
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
        }
        try {
            prepStatement = null;
            prepStatement = con.prepareStatement(ADD_USER_ROLE);
            prepStatement.setLong(1, generatedId);
            prepStatement.setString(2, "USER");
            result = (1 == prepStatement.executeUpdate());
            log.info("User {} with role  is added", user.getUsername());
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            log.info("SQLException during Add {} processing {}. {}", user.getUsername(), Utils.class, e.getMessage());
            throw e;
//            throw new DBException("User with a role is not added to the DB", e);
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
            Utils.close(con);
        }
    }

}

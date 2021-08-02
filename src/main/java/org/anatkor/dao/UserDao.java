package org.anatkor.dao;

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
    private static final String ADD_USER_AND_ROLE = "INSERT INTO usr (username, email, password)  VALUES (?,?,?); INSERT INTO user_role(user_id, role) VALUES ((SELECT id FROM usr WHERE login ='?'), 'USER')";
    private static final String FIND_ALL_USERS_AND_ROLES = "SELECT * From usr LEFT JOIN user_role ON usr.id = user_role.user_id;";

    final static Logger log = LogManager.getLogger(UserDao.class);

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection connection = Utils.getConnection();
        ResultSet rs = null;
//       Set ids is necessary to check for users with several roles
        Set<Long> ids = new HashSet<>();
        try (Statement stm = connection.createStatement()) {
            rs = stm.executeQuery(FIND_ALL_USERS_AND_ROLES);
            while (rs.next()) {
                long id = rs.getLong("id");
                String role = rs.getString("role");

                if (ids.add(id)) {
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
                            .withRoles(role)
                            .build();
                    users.add(user);
                } else {
                    for (User u : users) {
                        if (u.getId() == id) {
                            u.addRole(role);
                            users.set(users.indexOf(u), u);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", FIND_ALL_USERS_AND_ROLES, Utils.class, e);
        } finally {
            Utils.closeResultSet(rs);
        }
        Utils.closeConnection(connection);
        return users;
    }

    public void addUser(User user) {
        boolean result = false;
        Connection connection = Utils.getConnection();
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            prepStatement = connection.prepareStatement(ADD_USER, new String[]{"id"});
            prepStatement.setString(1, user.getUsername());
            prepStatement.setString(2, user.getEmail());
            prepStatement.setString(3, user.getPassword());
            prepStatement.executeUpdate();
            log.info("User {} is added", user.getUsername());
            rs = prepStatement.getGeneratedKeys();
            long generatedId = 0L;
            if (rs.next()) {
                generatedId = rs.getLong(1);
            }
            prepStatement = null;
            prepStatement = connection.prepareStatement(ADD_USER_ROLE);
            prepStatement.setLong(1, generatedId);
            prepStatement.setString(2, "USER");
            result = (1 == prepStatement.executeUpdate());
            log.info("User {} with role  is added", user.getUsername());
        } catch (SQLException e) {
            log.info("SQLException during Add user processing {}. {}", Utils.class, e.getMessage());
        } finally {
            Utils.closeStatement(prepStatement);
            Utils.closeResultSet(rs);
            Utils.closeConnection(connection);
        }
    }

}

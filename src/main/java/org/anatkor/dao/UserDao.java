package org.anatkor.dao;

import org.anatkor.model.User;
import org.anatkor.servlets.AdmServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDao {
    private static final String FIND_ALL_USERS = "SELECT * FROM usr;";
    private static final String FIND_ALL_USERS_AND_ROLES = "SELECT * From usr LEFT JOIN user_role ON usr.id = user_role.user_id;";

    final static Logger log = LogManager.getLogger(UserDao.class);

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        log.info("connection try");
        Connection connection = Utils.getConnection();
        ResultSet rs = null;
        Set<Long> ids = new HashSet<>();
        try (Statement stm = connection.createStatement()) {
            rs = stm.executeQuery(FIND_ALL_USERS_AND_ROLES);
            while (rs.next()) {
                long id = rs.getLong("id");
                String role = rs.getString("role");

                if(ids.add(id)) {
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
                    for (User u:users) {
                        if (u.getId() == id) {
                            u.addRole(role);
                            users.set(users.indexOf(u), u);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utils.closeResultSet(rs);
        }
        Utils.closeConnection(connection);
        return users;
    }
}

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
import java.util.List;

public class UserDao {
    private static final String FIND_ALL_USERS = "SELECT * FROM usr;";

    final static Logger log = LogManager.getLogger(UserDao.class);

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        log.info("connection try");
        Connection connection = Utils.getConnection();
        ResultSet rs = null;
        try (Statement stm = connection.createStatement()) {
            rs = stm.executeQuery(FIND_ALL_USERS);
            while (rs.next()) {
                long id = rs.getLong("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                LocalDateTime registrationDateTime = rs.getTimestamp("registered").toLocalDateTime();
                boolean active = rs.getBoolean("active");
                users.add(new User.UserBuilder()
                        .withId(id)
                        .withPassword(password)
                        .withUsername(username)
                        .withEmail(email)
                        .withRegistrationDateTime(registrationDateTime)
                        .withActive(active)
                        .build());
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

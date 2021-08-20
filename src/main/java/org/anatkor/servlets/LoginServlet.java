package org.anatkor.servlets;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.Role;
import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(LoginServlet.class);
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("User servlet called");
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("User servlet called");
        HttpSession session = req.getSession();
        Long phoneNumber = Long.parseLong("38" + req.getParameter("phone_number"));
        String password = req.getParameter("password");
        try {
            User user = userService.findUserByPhoneAndPassword(phoneNumber, password);
            session.setAttribute("user_auth", user);
            session.setAttribute("role", (user.getRole()).name());
            if (user.getRole().equals(Role.ADMIN)) {
                resp.sendRedirect("/admin");
            } else {
                resp.sendRedirect("/wallet");
            }
        } catch (DBException e) {
            req.setAttribute("warn", e.getMessage());
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }
}

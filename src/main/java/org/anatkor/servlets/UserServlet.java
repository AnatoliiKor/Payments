package org.anatkor.servlets;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class UserServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(UserServlet.class);
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("User servlet called");
        String username = req.getParameter("username");
        User user = null;

        try {
            user = userService.findUserByUsername (username);
        } catch (DBException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/jsp/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("User servlet called");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = null;
        try {
            user = userService.findUserByUsername (username);
            if (password.equals(user.getPassword())) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("/jsp/user.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Wrong password");
                req.setAttribute("username", username);
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);}

        } catch (DBException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }

    }
}

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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(LoginServlet.class);
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("User servlet called");
//        String username = req.getParameter("username");
//        req.setAttribute("username", username);
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("User servlet called");
        final HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            User user = userService.findUserByUsernamePassword (username, password);
            session.setAttribute("user_authenticated", user);
            resp.sendRedirect("/user");

        } catch (DBException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("username", username);
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }
}

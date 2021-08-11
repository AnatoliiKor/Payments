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
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            User user = userService.findUserByUsernamePassword(username, password);
            session.setAttribute("user_auth", user);
            session.setAttribute("user_auth_name", user);
            session.setAttribute("role", (user.getRole()).name());
            if (session.getAttribute("req_uri") != null) {
                String uri = (String) session.getAttribute("req_uri");
                session.removeAttribute("req_uri");
                resp.sendRedirect(uri);
            } else {
                resp.sendRedirect("/user");
            }
        } catch (DBException e) {
            req.setAttribute("warn", e.getMessage());
            req.setAttribute("username", username);
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }
}

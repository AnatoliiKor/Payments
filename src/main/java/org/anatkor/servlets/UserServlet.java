package org.anatkor.servlets;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.Role;
import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(UserServlet.class);
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("User servlet called");
        HttpSession session = req.getSession();
        ServletContext context = req.getServletContext();
        Long id;
        if (req.getParameter("id") !=null) {
            id = Long.valueOf(req.getParameter("id"));
            context.setAttribute("id", id);
        } else {
            id = (Long) context.getAttribute("id");
        }
        User userAuth = (User) session.getAttribute("user_auth");
        String role = (String) session.getAttribute("role");
        if (userAuth.getId() == id || role.equals("ADMIN")) {
            try {
                req.setAttribute("user", userService.findUserById(id));
            } catch (DBException e) {
                req.setAttribute("warn", e.getMessage());
            }
            req.getRequestDispatcher("/jsp/user.jsp").forward(req, resp);
        } else {
            req.setAttribute("warn", "User is not authorized. Log in");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);}
    }
}

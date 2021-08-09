package org.anatkor.servlets;

import org.anatkor.exceptions.DBException;
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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            if (userService.addUser(username, email, password)) {
                req.setAttribute("message", "User with username \"" + username + "\" registered successfully. Sign in");
                final HttpSession session = req.getSession();
                session.setAttribute("username", username);
//                req.setAttribute("username", username);
//                resp.sendRedirect("/login?username=" + username);
                resp.sendRedirect("/login");
            }
        } catch (DBException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
        }
    }
}

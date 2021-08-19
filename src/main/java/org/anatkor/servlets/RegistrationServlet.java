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

        String lastName = req.getParameter("last_name");
        String name = req.getParameter("name");
        String middleName = req.getParameter("middle_name");
        if (middleName==null) {middleName="";}
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        long phoneNumber = Long.parseLong("38" + req.getParameter("phone_number"));

        try {
            if (userService.addUser( lastName, name, middleName, password, email, phoneNumber)) {
                req.setAttribute("message", "Client \"" + lastName + " " + name + " " + middleName + "\" registered successfully. Sign in");
//                final HttpSession session = req.getSession();
//                session.setAttribute("username", username);
////                req.setAttribute("username", username);
////                resp.sendRedirect("/login?username=" + username);
                resp.sendRedirect("/login");
            }
        } catch (DBException e) {
            req.setAttribute("warn", e.getMessage());
            req.setAttribute("last_name", lastName);
            req.setAttribute("name", name);
            req.setAttribute("middle_name", middleName);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
        }
    }
}

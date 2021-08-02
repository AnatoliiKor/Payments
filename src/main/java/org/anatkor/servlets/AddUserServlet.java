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

@WebServlet("/registration")
public class AddUserServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(AddUserServlet.class);
    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("AddUser servlet called");
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password =  req.getParameter("password");

        try {
            userService.addUser(username, email, password);
        } catch (DBException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("error_reason", e.getCause().toString());
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
//            resp.sendRedirect("/error");
        }
        resp.sendRedirect("/users");
    }

}

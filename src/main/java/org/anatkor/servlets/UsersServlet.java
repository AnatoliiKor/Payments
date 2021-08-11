package org.anatkor.servlets;

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
import java.util.List;

@WebServlet("/admin/users")
public class UsersServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(UsersServlet.class);
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<User> users = userService.findAll();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/jsp/users_list.jsp").forward(req, resp);
    }

}

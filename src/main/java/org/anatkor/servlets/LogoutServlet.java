package org.anatkor.servlets;

import org.anatkor.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user_auth") != null) {
            User user = (User) session.getAttribute("user_auth");
            log.info("User {} Logout", user.getUsername());
            session.invalidate();
//            session.removeAttribute("user_auth");
//            session.removeAttribute("user_auth_name");
//            session.removeAttribute("role");
        }
        resp.sendRedirect("/login");
    }
}
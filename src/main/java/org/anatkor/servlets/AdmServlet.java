package org.anatkor.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adm")
public class AdmServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(AdmServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Adm servlet called");
        String a = req.getParameter("fr");
        if (a != null) {
            System.out.println(a);
        }
        resp.sendRedirect("/");
    }

}

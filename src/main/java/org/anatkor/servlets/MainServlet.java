package org.anatkor.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = "/")
public class MainServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(MainServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
//        resp.sendRedirect("/redirect to another servlet");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

//    @Override
//    public void init() throws ServletException {
//        super.init();
//    }

//    @Override
//    public void destroy() {
//        log.info("Main servlet Destroyed");
//    }
}

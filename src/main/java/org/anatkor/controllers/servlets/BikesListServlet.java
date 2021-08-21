package org.anatkor.controllers.servlets;

import org.anatkor.model.Bike;
import org.anatkor.services.BikeService;
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

@WebServlet("/bikes")
public class BikesListServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(BikesListServlet.class);
    BikeService bikeService = new BikeService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Bike servlet called");

        HttpSession session = req.getSession();
        String sortBy;
        String order;
        if (req.getParameter("sort_by") != null) {
            sortBy = req.getParameter("sort_by");
            session.setAttribute("sort_by", sortBy);
        } else if (session.getAttribute("sort_by") != null) {
            sortBy = (String) session.getAttribute("sort_by");
        } else {
            sortBy = "date";
        }

        if (req.getParameter("order") != null) {
            order = req.getParameter("order");
            session.setAttribute("order", order);
        } else if (session.getAttribute("order") != null) {
            order = (String) session.getAttribute("order");
        } else {
            order = "DESC";
        }

        String page = req.getParameter("pg");
        if (page == null || page.equals("")) {
            req.setAttribute("pg", 1);
        } else {
            req.setAttribute("pg", Integer.parseInt(page));
        }

        List<Bike> bikes = bikeService.findAll(sortBy, order);
        req.setAttribute("sort_by", sortBy);
        req.setAttribute("order", order);
        req.setAttribute("bikes", bikes);
        req.getRequestDispatcher("/jsp/bikes_list.jsp").forward(req, resp);
    }

}

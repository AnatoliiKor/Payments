package org.anatkor.servlets;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.Bike;
import org.anatkor.services.BikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bikes")
public class BikesListServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(BikesListServlet.class);
    BikeService bikeService = new BikeService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Bike servlet called");
        if (req.getParameter("message")!=null) {
            req.setAttribute("message", req.getParameter("message"));
        }
        if (req.getParameter("warn")!=null) {
            req.setAttribute("warn", req.getParameter("warn"));
        }
        String sortBy = "date";
        String order = "DESC";

        String page = req.getParameter("pg");
        if (page == null || page.equals("")) {
            req.setAttribute("pg", 1);
        } else {
            req.setAttribute("pg", Integer.parseInt(page));
        }
        if (req.getParameter("sort_by")!= null) {sortBy = req.getParameter("sort_by");}
        if (req.getParameter("order")!= null) {order = req.getParameter("order");}
        List<Bike> bikes = bikeService.findAll(sortBy, order);
        req.setAttribute("sort_by", sortBy);
        req.setAttribute("order", order);
        req.setAttribute("bikes", bikes);
        req.getRequestDispatcher("/jsp/bikes_list.jsp").forward(req, resp);
    }

}

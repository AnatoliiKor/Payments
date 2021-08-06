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

@WebServlet("/bike_delete")
public class BikeDeleteServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(BikeDeleteServlet.class);
    BikeService bikeService = new BikeService();


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("BikeServlet called");
//        if (req.getParameter("id") != null) {
//            Long bikeId = Long.valueOf(req.getParameter("id"));
//            Bike bike = bikeService.findBikeById(bikeId);
//            req.setAttribute("bike", bike);
//        }
//        req.getRequestDispatcher("/jsp/new_bike.jsp").forward(req, resp);
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("DeleteBike servlet called");
        Long id = Long.valueOf(req.getParameter("id"));

        try {
            if (bikeService.deleteBike(id)) {
                resp.sendRedirect("/bikes?message=Deletion is successful");
            }
        } catch (DBException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("error_reason", e.getCause().toString());
            req.getRequestDispatcher("/jsp/new_bike.jsp").forward(req, resp);
        }


    }
}

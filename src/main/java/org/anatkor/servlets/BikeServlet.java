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

@WebServlet("/bike_edit")
public class BikeServlet extends HttpServlet {

    final static Logger log = LogManager.getLogger(BikeServlet.class);
    BikeService bikeService = new BikeService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("BikeServlet called");

        if (req.getParameter("id") != null) {
            Long bikeId = Long.valueOf(req.getParameter("id"));
            Bike bike = bikeService.findBikeById(bikeId);
            req.setAttribute("bike", bike);
        }
        req.getRequestDispatcher("/jsp/new_bike.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("UpdateBike servlet called");
        req.setCharacterEncoding("UTF-8");
        Long id;

        if (req.getParameter("id") != null) {
            id = Long.valueOf(req.getParameter("id"));
            //TODO Doesn't interrupt the method like return
            // if ("delete".equals(req.getParameter("action"))) {
//           bikeService.deleteBike(id)
//           req.setAttribute("error", "Deletion is successful");
//            resp.sendRedirect("/bikes?message=Deletion is successful");
        } else {
            id = -1L;
        }
        String name = req.getParameter("name");
        String brand = req.getParameter("brand");
        String category = req.getParameter("category");
        String colour = req.getParameter("colour");
        String description = req.getParameter("description");
        int price = Integer.valueOf(req.getParameter("price"));
        try {
            if (bikeService.newBike(id, name, brand, category, colour, description, price)) {
                resp.sendRedirect("/bikes?message=Bike is processed");
            }
        } catch (DBException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("error_reason", e.getCause().toString());
            req.getRequestDispatcher("/jsp/new_bike.jsp").forward(req, resp);
//            resp.sendRedirect("/error");
        }
    }

}

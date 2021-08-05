package org.anatkor.services;

import org.anatkor.dao.BikeDao;
import org.anatkor.dao.UserDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Bike;
import org.anatkor.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class BikeService {

    private BikeDao bikeDao = new BikeDao();

    public List<Bike> findAll(String sortBy, String order) {
        return bikeDao.findAll(sortBy, order);
    }


    public void newBike(Long id, String name, String brand, String category, String colour, String description, int price) throws DBException {
        Bike bike = new Bike.Builder()
                .withId(id)
                .withName(name)
                .withBrand(brand)
                .withCategory(category)
                .withColour(colour)
                .withDescription(description)
                .withPrice(price)
                .withRegistrationDateTime(LocalDateTime.now())
                .build();
        bikeDao.newBike(bike);
    }

    public Bike findBikeById(Long bikeId) {
        return bikeDao.findBikeById(bikeId);
    }
}

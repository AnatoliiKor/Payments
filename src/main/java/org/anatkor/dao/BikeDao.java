package org.anatkor.dao;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.Bike;
import org.anatkor.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BikeDao {
    private static final String FIND_ALL_BIKES = "SELECT * FROM bike;";
    private static final String ADD_BIKE_ROLE = "INSERT INTO user_role VALUES (?,?)";
    private static final String ADD_BIKE = "INSERT INTO bike (name, brand, category, colour, description, price)  VALUES (?,?,?,?,?,?)";
//    private static final String FIND_ALL_BIKES_AND_ROLES = "SELECT * From usr LEFT JOIN bike_role ON usr.id = bike_role.bike_id;";

    final static Logger log = LogManager.getLogger(BikeDao.class);

    public List<Bike> findAll() {
        List<Bike> bikes = new ArrayList<>();
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = Utils.getConnection();
            stm = con.createStatement();
            rs = stm.executeQuery(FIND_ALL_BIKES);
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String brand = rs.getString("brand");
                String category = rs.getString("category");
                String colour = rs.getString("colour");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                LocalDateTime registrationDateTime = rs.getTimestamp("date").toLocalDateTime();

                Bike bike = new Bike.Builder()
                        .withName(name)
                        .withBrand(brand)
                        .withCategory(category)
                        .withColour(colour)
                        .withDescription(description)
                        .withPrice(price)
                        .withRegistrationDateTime(registrationDateTime)
                        .build();
                bikes.add(bike);
            }
        } catch (SQLException e) {
            log.debug("SQLException during Query {} processing from {}.", FIND_ALL_BIKES, Utils.class, e);
        } finally {
            Utils.close(rs);
            Utils.close(stm);
            Utils.close(con);
        }
        return bikes;
    }

    public void newBike(Bike bike) throws DBException {
        boolean result = false;
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet rs = null;
        try {
            con = Utils.getConnection();
            prepStatement = con.prepareStatement(ADD_BIKE, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            prepStatement.setString(k++, bike.getName());
            prepStatement.setString(k++, bike.getBrand());
            prepStatement.setString(k++, bike.getCategory());
            prepStatement.setString(k++, bike.getColour());
            prepStatement.setString(k++, bike.getDescription());
            prepStatement.setInt(k++, bike.getPrice());
            if (prepStatement.executeUpdate() > 0) {
                log.info("Bike {} is added", bike.getName());
                result = true;
                rs = prepStatement.getGeneratedKeys();
                long generatedId;
                if (rs.next()) {
                    generatedId = rs.getLong(1);
                    bike.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            log.info("SQLException during Add {} processing {}. {}", bike.getName(), Utils.class, e.getMessage());
            throw new DBException("Bike is not added to the DB", e);
        } finally {
            Utils.close(rs);
            Utils.close(prepStatement);
            Utils.close(con);
        }
    }

}

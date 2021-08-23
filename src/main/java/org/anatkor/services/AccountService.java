package org.anatkor.services;

import org.anatkor.dao.AccountDao;
import org.anatkor.dao.BikeDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Account;
import org.anatkor.model.Bike;

import java.time.LocalDateTime;
import java.util.List;

public class AccountService {

    private AccountDao accountDao = new AccountDao();


    public boolean newAccount(String accountName, String currency, Long user_id) {
        Account account = new Account();
        account.setAccountName(accountName);
        account.setCurrency(Account.CURRENCY.valueOf(currency));
        account.setUserId(user_id);
        return accountDao.NewAccount(account);
    }

    public List<Account> findAllByUserId(Long user_id, String sortBy, String order) {
            return accountDao.findAllByUserId(user_id, sortBy, order);
        }

//    public List<Bike> findAll(String sortBy, String order) {
//        return bikeDao.findAll(sortBy, order);
//    }
//
//
//    public boolean newBike(Long id, String name, String brand, String category, String colour, String description, int price)
//            throws DBException {
//        Bike bike = new Bike.Builder()
//                .withId(id)
//                .withName(name)
//                .withBrand(brand)
//                .withCategory(category)
//                .withColour(colour)
//                .withDescription(description)
//                .withPrice(price)
//                .withRegistrationDateTime(LocalDateTime.now())
//                .build();
//        return bikeDao.newBike(bike);
//    }
//
//    public Bike findBikeById(Long bikeId) {
//        return bikeDao.findBikeById(bikeId);
//    }
//
//    public boolean deleteBike(Long id) throws DBException {
//        return bikeDao.deleteBike(id);
//    }
}

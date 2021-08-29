package org.anatkor.services;

import org.anatkor.dao.AccountDao;
import org.anatkor.model.Account;
import org.anatkor.model.enam.Currency;
import java.util.List;

public class AccountService {

    private AccountDao accountDao = new AccountDao();


    public boolean newAccount(String accountName, String currency, Long user_id) {
        Account account = new Account();
        account.setAccountName(accountName);
        account.setCurrency(Currency.valueOf(currency));
        account.setUserId(user_id);
        return accountDao.newAccount(account);
    }

    public List<Account> findAllAccountsByUserIdSorted(Long user_id, String sortBy, String order) {
            return accountDao.findAllAccountsByUserIdSorted(user_id, sortBy, order);
        }

    public List<Account> findAllAccountsByUserId(Long user_id) {
        return accountDao.findAllAccountsByUserId(user_id);
    }

    public Account findById(Long id) {
        return accountDao.findById(id);
    }

    public List<Account> findAllAccountsToDo() {
        return accountDao.findAllAccountsToDo();
    }

    public boolean updateAccountActiveById(Long account_id, Boolean accountActive) {
        return accountDao.updateAccountActiveById(account_id, accountActive);
    }

    public boolean updateAccountActionById(Long account_id,int accountAction) {
        return accountDao.updateAccountActionById(account_id, accountAction);
    }

    public boolean updateAccountBalanceById(Long account_id, int amount) {
        return accountDao.updateAccountBalanceById(account_id, amount);
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

package org.anatkor.model;

import java.util.List;

public class Account {
    private Long id;
    private int number;
    private User user;
    private String accountName;
    private long balance;
    private boolean active;
    private CURRENCY currency;
    private CreditCard creditCard;
    private List<Payment> payment;

    public Account() {
    }

    public Account(User user) {
        this.user = user;
    }

    public enum CURRENCY {
        UAH, USD, EURO
    }

}

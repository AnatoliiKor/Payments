package org.anatkor.model;

import org.anatkor.model.enums.Currency;
import org.anatkor.utils.Util;
import java.time.LocalDateTime;
import java.util.List;

public class Account {
    private Long id;
    private Long number;
    private long balance;
    private String accountName;
    private Currency currency;
    private LocalDateTime registered;
    private boolean active;
    private Long userId;
    /*
     * if 0 - nothing to do
     * if 1 - unblock request
     * if 2 - block request
     * */
    private int action;
    private long cardNumber;
    private List<Payment> payment;


    public Account() {
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFormatedDate() {
        return Util.getFormattedDate(registered);
    }

    public String getCardNumberSpaces() {
        String number = String.valueOf(this.cardNumber);
        if (number == null) return null;
        char delimiter = ' ';
        return number.replaceAll(".{4}(?!$)", "$0" + delimiter);
    }
}

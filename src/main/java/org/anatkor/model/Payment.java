package org.anatkor.model;

import org.anatkor.model.enums.Currency;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    private long id;
    private long sourceAccount;
    private long billAccount;
    private LocalDateTime registered;
    private String paymentDestination;
    private int amount;
    private Currency currency;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(long sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public long getBillAccount() {
        return billAccount;
    }

    public void setBillAccount(long billAccount) {
        this.billAccount = billAccount;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public String getPaymentDestination() {
        return paymentDestination;
    }

    public void setPaymentDestination(String paymentDestination) {
        this.paymentDestination = paymentDestination;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public String getFormatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return this.registered.format(formatter);
    }
}

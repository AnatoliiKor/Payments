package org.anatkor.model;

import java.time.LocalDateTime;
import java.util.List;

public class CreditCard {
    private Long id;
    private Long cardNumber;
    private String cardName;
    private boolean active;

    public CreditCard() {
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

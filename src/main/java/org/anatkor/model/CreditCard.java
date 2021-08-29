package org.anatkor.model;

import java.time.LocalDateTime;
import java.util.List;

public class CreditCard {
    private Long id;
    private Long account_id;

    public CreditCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }
}

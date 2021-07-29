package org.anatkor.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class User {
    private Long id;

    private String username;
    private String password;
    private String password2;
    private String email;

    private boolean active;
    private LocalDate registrationDate;

    private List<Cart> carts;

    private Set<Role> roles;

    public User() {
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getRegistrationDate() {
        if (registrationDate==null) registrationDate = LocalDate.now();
        return registrationDate;
    }

    public String getEmail() {
//        if (email == null) this.setEmail("No email");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate() {
        this.registrationDate = LocalDate.now();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}

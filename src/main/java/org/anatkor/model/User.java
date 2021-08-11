package org.anatkor.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private Long id;

    private String username;
    private String password;
    private String email;
    private LocalDateTime registrationDateTime;
    private boolean active;
    private Role role;

    private List<Cart> carts;


    public User() {
    }

    public static class UserBuilder {
        private User newUser;

        public UserBuilder() {
            newUser = new User();
        }

        public UserBuilder withId(Long id){
            newUser.id = id;
            return this;
        }

        public UserBuilder withUsername(String username){
            newUser.username = username;
            return this;
        }

        public UserBuilder withPassword(String password){
            newUser.password = password;
            return this;
        }
        public UserBuilder withEmail(String email){
            newUser.email = email;
            return this;
        }

        public UserBuilder withRegistrationDateTime(LocalDateTime registrationDateTime){
            newUser.registrationDateTime = registrationDateTime;
            return this;
        }

        public UserBuilder withActive(Boolean active){
            newUser.active = active;
            return this;
        }

        public UserBuilder withRole(Role role){
            newUser.role = role;
            return this;
        }

        public UserBuilder withCarts(List<Cart> carts){
            newUser.carts = carts;
            return this;
        }

        public User build(){
            return newUser;
        }

    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    public enum ROLE {
//        USER, ADMIN, UNKNOWN
//    }
}


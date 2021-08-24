package org.anatkor.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class User {
    private Long id;

    private String lastName;
    private String name;
    private String middleName;
    private String password;
    private String email;
    private long phoneNumber;
    private LocalDateTime registered;
    private boolean active;
    private Role role;
    private List<Account> accounts;

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

        public UserBuilder withLastName(String lastName){
            newUser.lastName = lastName;
            return this;
        }

        public UserBuilder withName(String name){
            newUser.name = name;
            return this;
        }

        public UserBuilder withMiddleName(String middleName){
            newUser.middleName = middleName;
            return this;
        }

        public UserBuilder withPassword(String password){
            newUser.password = password;
            return this;
        }

        public UserBuilder withPhoneNumber(long phoneNumber){
            newUser.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder withEmail(String email){
            newUser.email = email;
            return this;
        }

        public UserBuilder withRegistrationDateTime(LocalDateTime registrationDateTime){
            newUser.registered = registrationDateTime;
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

        public UserBuilder withAccounts(List<Account> accounts){
            newUser.accounts = accounts;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getFormatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return this.registered.format(formatter);
    }


}


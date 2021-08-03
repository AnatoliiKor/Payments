package org.anatkor.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Bike {

    private long id;

    private String category;
    private String brand;
    private String name;
    private String colour;
    private String description;
    private Integer price;
    private LocalDateTime registrationDateTime;
//    private String filename;


//    @OneToMany(mappedBy = "bike", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<CartItem> cartItems;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    public static class Builder {
        private Bike newBike;

        public Builder() {
            newBike = new Bike();
        }

        public Builder withCategory(String category){
            newBike.category = category;
            return this;
        }

        public Builder withBrand(String brand){
            newBike.brand = brand;
            return this;
        }

        public Builder withRegistrationDateTime(LocalDateTime registrationDateTime){
            newBike.registrationDateTime = registrationDateTime;
            return this;
        }
        public Builder withName(String name){
            newBike.name = name;
            return this;
        }
        public Builder withColour(String colour){
            newBike.colour = colour;
            return this;
        }

        public Builder withDescription(String description){
            newBike.description = description;
            return this;
        }

        public Builder withPrice(Integer price){
            newBike.price = price;
            return this;
        }

        public Bike build(){
            return newBike;
        }

    }


}

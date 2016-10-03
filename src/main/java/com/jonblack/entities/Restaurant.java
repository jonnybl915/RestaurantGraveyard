package com.jonblack.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by jonathandavidblack on 10/3/16.
 */
@Entity
public class Restaurant {
    @Id
    @GeneratedValue
    int id;

    @Column
    String RestaurantName;

    @Column
    String address;

    public Restaurant() {
    }

    public Restaurant(int id, String restaurantName, String address) {
        this.id = id;
        RestaurantName = restaurantName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

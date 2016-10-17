package com.jonblack.controllers;

import com.jonblack.entities.Restaurant;
import com.jonblack.entities.User;
import com.jonblack.services.RestaurantRepository;
import com.jonblack.services.UserRepository;
import com.jonblack.utilities.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by jonathandavidblack on 10/3/16.
 */
@RestController
public class RestaurantGraveyardRestController {

    @Autowired
    UserRepository users;

    @Autowired
    RestaurantRepository restaurants;

    @PostConstruct
    public void init() throws SQLException {
        Server.createWebServer("-webPort", "1777").start();


    }

    @RequestMapping(path="/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, Integer itemToEdit, Integer id) {

        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user, HttpSession session) throws Exception {
        User userFromDatabase = users.findFirstByUsername(user.getUsername());

        if (userFromDatabase == null) {
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            user.setUsername(user.getUsername());
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(user.getPassword(), userFromDatabase.getPassword())) {
            return new ResponseEntity<>("BAD PASS", HttpStatus.FORBIDDEN);
        }

        session.setAttribute("username", user.getUsername());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(path = "/restaurants", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Restaurant>> displayRestaurants(/* not sure what I need*/ HttpSession session) {
        ArrayList<Restaurant> allRestaurants = (ArrayList<Restaurant>) restaurants.findAll();
        return new ResponseEntity<> (allRestaurants, HttpStatus.ACCEPTED);
    }
    @RequestMapping(path = "/addRestaurant", method = RequestMethod.POST)
    public ResponseEntity<Restaurant> addRestaurantToMap(@RequestBody Restaurant restaurant, HttpSession session) {
        Restaurant rest = new Restaurant();

        rest.setAddress(restaurant.getAddress());
        rest.setId(restaurant.getId());
        rest.setRestaurantName(restaurant.getRestaurantName());

        restaurants.save(rest);

        return new ResponseEntity<>(rest, HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpSession session) {
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

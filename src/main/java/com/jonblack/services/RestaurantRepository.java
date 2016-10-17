package com.jonblack.services;

import com.jonblack.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jonathandavidblack on 10/14/16.
 */
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

}

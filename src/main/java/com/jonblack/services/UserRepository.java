package com.jonblack.services;

import com.jonblack.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jonathandavidblack on 10/14/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByUsername(String username);
}

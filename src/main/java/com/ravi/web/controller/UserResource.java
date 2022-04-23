package com.ravi.web.controller;

import com.ravi.web.exception.UserNotFoundException;
import com.ravi.web.model.User;
import com.ravi.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable(value = "id") long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id : "+userId));
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping(value = "/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id){
        User update = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id : "+id));
        update.setFirstName(user.getFirstName());
        update.setLastName(user.getLastName());
        update.setEmail(user.getEmail());
        return userRepository.save(update);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id : "+id));
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

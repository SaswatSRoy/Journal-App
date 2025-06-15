package com.saswat.journalApp.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.saswat.journalApp.api.response.WeatherResponse;
import com.saswat.journalApp.entity.User;
import com.saswat.journalApp.repository.UserRepository;
import com.saswat.journalApp.service.UserEntryService;
import com.saswat.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserEntryController {

    @Autowired
    private UserEntryService userService;

    @Autowired
    private UserRepository repo;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/get-users")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.FOUND);
    }

    @GetMapping("/id/{uid}")
    public ResponseEntity<User> findByUID(@PathVariable ObjectId uid){
        Optional<User> user= userService.findById(uid);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(),HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PutMapping("/update")
    public ResponseEntity<?>updateUser(@RequestBody User user){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String username= auth.getName();
        User userInDb= userService.findByName(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        repo.deleteByUsername(auth.getName());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greetingUser() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weather = weatherService.getWeather("Mumbai");
        double feelsLike = weather.getCurrent().getFeelslike_c();
        String message = "Hi " + authentication.getName() + ", Weather feels like " + feelsLike + "°C in Mumbai.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }




}

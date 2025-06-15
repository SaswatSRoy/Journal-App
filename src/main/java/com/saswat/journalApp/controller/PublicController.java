package com.saswat.journalApp.controller;

import com.saswat.journalApp.entity.User;
import com.saswat.journalApp.service.UserDetailsServiceImpl;
import com.saswat.journalApp.service.UserEntryService;
import com.saswat.journalApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserEntryService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserEntryService service;

    @Autowired
    private JwtUtil jwt;

    @PostMapping("signup")
    public ResponseEntity<User> signup(@RequestBody User user){
        try{
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                   user.getUsername(),user.getPassword()
           ));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String s= jwt.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(s, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


}

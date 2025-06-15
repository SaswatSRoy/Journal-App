package com.saswat.journalApp.controller;

import com.saswat.journalApp.cache.AppCache;
import com.saswat.journalApp.entity.User;
import com.saswat.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all=userEntryService.getAll();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/create-admin")
    public ResponseEntity<?> createAdminUser(@RequestBody User user){
        userEntryService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/clear-cache")
    public void clearCache(){
        appCache.init();
    }

}

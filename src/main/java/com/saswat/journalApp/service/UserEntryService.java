package com.saswat.journalApp.service;

import com.saswat.journalApp.entity.JournalEntry;
import com.saswat.journalApp.entity.User;
import com.saswat.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserEntryService {

    @Autowired
    private UserRepository userRepo;

    private static final PasswordEncoder passwordencoder= new BCryptPasswordEncoder();

    public void saveEntry (User user){
        userRepo.save(user);
    }

    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordencoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepo.save(user);
            return true;
        }catch (Exception e){
            log.info("ahahahaha");
            return false;
        }
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public User findByName(String username){
        return userRepo.findByUsername(username);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        userRepo.save(user);
    }
}

package com.saswat.journalApp.service;

//Best practice, has business logic of the database

// Controller ---> Service ---> Repository

import com.saswat.journalApp.entity.JournalEntry;
import com.saswat.journalApp.entity.User;
import com.saswat.journalApp.repository.JournalEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired // Dependency Injection
    private JournalEntryRepo repository;

    @Autowired
    private UserEntryService userEntryService;

    @Transactional // The one we studied in DBMS. After this go to the main class and give the @EnableTransactionManagement annotation to complete it.
    public void saveEntry(JournalEntry journalEntry, String username){
        User user=userEntryService.findByName(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry save=repository.save(journalEntry);
        user.getJournalEntries().add(save);
        userEntryService.saveNewUser(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        repository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return repository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId myId, String username){
        try{
            User user=userEntryService.findByName(username);
            boolean removed=user.getJournalEntries().removeIf(x-> x.getId().equals(myId));
            if(removed){
                userEntryService.saveNewUser(user);
                repository.deleteById(myId);
            }
        }catch (Exception e){
            log.info("Error ",e);
            throw  new RuntimeException("An error occured while deleting: "+e);
        }



    }

    public List<JournalEntry>findByUsername(String username){ // Done to ensure we get the correct journal entry id
        return new ArrayList<>();
    }



}

package com.saswat.journalApp.controller;


import com.saswat.journalApp.entity.JournalEntry;
import com.saswat.journalApp.entity.User;
import com.saswat.journalApp.service.JournalEntryService;
import com.saswat.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerActual {

    @Autowired // Dependency Injection
    private JournalEntryService service;

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/get-journal")
    public ResponseEntity<?> getAllJournalEntriesOfUser(){// localhost:8080/journal/get-journal
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username= auth.getName();
        User user=userEntryService.findByName(username);
        List<JournalEntry> entries=user.getJournalEntries();
        if(!entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry){// localhost:8080/journal/post-journal

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username= auth.getName();
            service.saveEntry(entry,username);
            return new ResponseEntity<>(entry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping ("/id/{myId}") // Searching by ID
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){ // localhost:8080/journal/id/ id given by us
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username= auth.getName();
        User user =userEntryService.findByName(username);
        List<JournalEntry>journalEntries=user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).toList(); // Find by id
        if(!journalEntries.isEmpty()){
            Optional <JournalEntry> journalEntry = service.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{myId}") //Deleting by Id
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username= auth.getName();
        service.deleteById(myId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id,@RequestBody JournalEntry entry){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username= auth.getName();
        JournalEntry old= service.findById(id).orElse(null);
        User user =userEntryService.findByName(username);
        List<JournalEntry>collection=user.getJournalEntries().stream().filter(x-> x.getId().equals(id)).toList();// Find by id
        if (!collection.isEmpty()){
            Optional <JournalEntry> journalEntry = service.findById(id);
            if(journalEntry.isPresent()){
                assert old != null;
                old.setTitle(entry.getTitle()!=null && !entry.getTitle().isEmpty() ? entry.getTitle() : old.getTitle());
                old.setContent(entry.getContent()!=null && !entry.getContent().isEmpty() ? entry.getContent() : old.getContent());
                service.saveEntry(entry);
                return new ResponseEntity<>(entry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}

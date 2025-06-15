package com.saswat.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")

@Data
public class User {

    @Id
    private ObjectId userId;

    @Indexed(unique = true) // The userName needs to be unique , hence we are using this annotation to ensure it is unique
    @NonNull                // The username and the password should not be null, hence this annotation is used
    private String username;
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;

    @DBRef // Used as a bridge between the users and journal_entries. Basically, a reference of the journal entries is created in the users document.
    private List<JournalEntry> journalEntries = new ArrayList<>(); // The list while initialized is empty
    private List<String> roles;
}

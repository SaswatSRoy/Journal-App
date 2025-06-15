package com.saswat.journalApp.repository;


import com.saswat.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// Spring Data MongoDB has its own repository (predefined) for this purpose,
// That is extending the interface provided by MongoDB to your manual interface and use acc.
public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {

    //The MongoRepository interface getting extended needed two entries , which are
    //The Entity and the primary key of the Entity [collection]
    //In this case it is the JournalEntry

}


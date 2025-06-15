package com.saswat.journalApp.entity;

import com.saswat.journalApp.enums.Sentiment;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//Plain Old java object [POJO]

@Document (collection = "journal_entries")// Equivalent to a row in a database but in MongoDB

//Lombok
@Getter
@Setter
public class JournalEntry {

    @Id // Maps the id as primary key
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

}

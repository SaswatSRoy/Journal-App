package com.saswat.journalApp.scheduler;

import com.saswat.journalApp.entity.JournalEntry;
import com.saswat.journalApp.entity.User;
import com.saswat.journalApp.enums.Sentiment;
import com.saswat.journalApp.service.EmailService;
import com.saswat.journalApp.service.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService mail;

    @Autowired
    private UserRepoImpl repo;



//    @Scheduled(cron = "0 0 9 ? * SUN *")
    public void fetchUsersAndSaMail(){
        List<User> users= repo.getUserForSA();
        for(User user:users){
            List<JournalEntry>journalEntries=user.getJournalEntries();
            List<Sentiment> collect = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(JournalEntry::getSentiment).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
            for (Sentiment sentiment:collect){
                if(sentiment !=null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }

            Sentiment mostFrequentSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer>entry:sentimentCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount= entry.getValue();;
                    mostFrequentSentiment=entry.getKey();
                }
            }
            if(mostFrequentSentiment !=null){
                mail.sendMain(user.getEmail(), "Sentiment for last 7 days",mostFrequentSentiment.toString());
            }

        }

    }


}

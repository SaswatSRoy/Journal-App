package com.saswat.journalApp.service;

import com.saswat.journalApp.entity.User;
import com.saswat.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepo;


    @Disabled
    @Test
    public void testFindByUsername(){
        User user= userRepo.findByUsername("Mama");
        Assertions.assertNotNull(user.getUsername());
        Assertions.assertTrue(user.getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,3",
            "343,23,13123"
    })
    public void test(int expected,int a,int b){
        Assertions.assertEquals(expected,a+b);

    }

}

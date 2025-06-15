package com.saswat.journalApp.repo;


import com.saswat.journalApp.service.UserRepoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepoImplTest {


    @Autowired
    private UserRepoImpl impl;

    @Test
    public void testUserForSA(){
        Assertions.assertNotNull(impl.getUserForSA());
    }
}

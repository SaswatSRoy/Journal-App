package com.saswat.journalApp.service;

import com.saswat.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl impl;

    @Mock
    private UserRepository repo;

    @Disabled
    @BeforeEach
    void setImpl(){
        MockitoAnnotations.initMocks(repo);
    }

    @Disabled
    @Test
    void loadUserByUsernameTest(){
        when(repo.findByUsername(ArgumentMatchers.anyString())).thenReturn((com.saswat.journalApp.entity.User) User.builder().username("ram").password("sasawa").build());
        UserDetails user= impl.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }

}

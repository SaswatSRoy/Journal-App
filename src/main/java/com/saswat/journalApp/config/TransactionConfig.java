package com.saswat.journalApp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // Spring boot searches the Transactional annotated methods and create a transactional context for each, from the IOC container
class TransactionConfig {

    @Bean
    public PlatformTransactionManager ptm(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
}

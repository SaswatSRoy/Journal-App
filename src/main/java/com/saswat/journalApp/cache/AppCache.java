package com.saswat.journalApp.cache;

import com.saswat.journalApp.entity.CacheEntity;
import com.saswat.journalApp.repository.CacheRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private CacheRepository repository;

    public Map<String ,String>appCache;


    @PostConstruct
    public void init(){
        appCache=new HashMap<>();
        List<CacheEntity> all = repository.findAll();

        for (CacheEntity entity:all) {
            appCache.put(entity.getKey(), entity.getValue());
        }

    }
}

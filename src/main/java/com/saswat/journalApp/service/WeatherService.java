package com.saswat.journalApp.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.saswat.journalApp.api.response.WeatherResponse;
import com.saswat.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private final String apiKey;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherService(@Value("${weather.api.key}") String  apiKey) {
        this.apiKey = apiKey;
    }

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) throws JsonProcessingException {
        WeatherResponse weatherResponse=redisService.get("weather_of_"+city,WeatherResponse.class);
        if(weatherResponse !=null){
            return weatherResponse;
        }else{
            String finalApi=appCache.appCache.get("weather_api").replace("<city>",city).replace("<apiKey>",apiKey);
            ResponseEntity<WeatherResponse>response=restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
            if(response.hasBody()){
                redisService.set("weather_of_"+city, response.getBody(),300l);
            }
            return response.getBody();
        }

    }

}

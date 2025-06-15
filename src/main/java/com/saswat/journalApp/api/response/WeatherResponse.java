package com.saswat.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

    public Location location;
    public Current current;



    @Getter
    @Setter
    public static class Current{

        @JsonProperty("temp_c")
        private double tempC;
        private double temp_f;
        private int is_day;

        private double wind_mph;
        private double wind_kph;
        private int wind_degree;
        private String wind_dir;
        private int pressure_mb;
        private double pressure_in;
        private double precip_mm;
        private double precip_in;
        private int humidity;
        private int cloud;
        private double feelslike_c;
        private double feelslike_f;
        private double windchill_c;
        private double windchill_f;
        private double heatindex_c;
        private int heatindex_f;
        private int dewpoint_c;
        private double dewpoint_f;
        private int vis_km;
        private int vis_miles;
        private double uv;
        private double gust_mph;
        private double gust_kph;
    }

    @Getter
    @Setter
    public static class Location{
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        private String tz_id;
        private int localtime_epoch;
        private String localtime;
    }

}





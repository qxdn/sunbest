package com.qianxu.sunbest;

import com.qianxu.sunbest.config.WeatherConfig;
import com.qianxu.sunbest.model.SolarMsg;
import com.qianxu.sunbest.model.WeatherMsg;
import com.qianxu.sunbest.service.api.WeatherService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherTests {

    @Autowired
    WeatherConfig weatherConfig;

    @Autowired
    WeatherService weatherService;

    @Test
    public void MyWeatherTest(){
        System.out.println(weatherConfig);
    }

    @Test
    public void WeatherServiceSolarTest(){
        SolarMsg solarMsg=weatherService.getSolar(30.59,114.30,24);
        System.out.println(solarMsg);
    }

    @Test
    public void WeatherServiceWeatherTest(){
        WeatherMsg weatherMsg=weatherService.getWeather(30.59,114.30);
        System.out.println(weatherMsg);
    }
}
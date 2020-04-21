package com.qianxu.sunbest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
@PropertySource("classpath:weather.properties")
@ConfigurationProperties(prefix="weather")
public class WeatherConfig {
    private String freeUrl;

    private String apiUrl;

    private String apiKey;

    private String freeKey;

    private Double lat;

    private Double lon;
}
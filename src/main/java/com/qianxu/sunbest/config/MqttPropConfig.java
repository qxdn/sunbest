package com.qianxu.sunbest.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Getter
@Setter
@ToString
@Configuration
@PropertySource("classpath:mqtt.properties")
@ConfigurationProperties(prefix="mqtt")
public class MqttPropConfig {
    private String username;
    private String password;
    private String ClientId;
    private List<String> subTopic;
    private List<String> pubTopic;
    private String tcpHost;
}
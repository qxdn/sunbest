package com.qianxu.sunbest.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    public CloseableHttpClient getHttpClient(){
        return HttpClientBuilder.create().build();
    }
}
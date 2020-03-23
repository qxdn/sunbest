package com.qianxu.sunbest.config;

import com.qianxu.sunbest.component.MqttSub;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MqttConfig {

    @Autowired
    MqttPropConfig mqttPropConfig;

    @Bean
    public MqttConnectOptions mqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
        mqttConnectOptions.setCleanSession(false);
        // 设置连接的用户名
        mqttConnectOptions.setUserName(mqttPropConfig.getUsername());
        // 设置连接的密码
        mqttConnectOptions.setPassword(mqttPropConfig.getPassword().toCharArray());
        // 设置会话心跳时间 单位为秒 服务器会每隔90秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        mqttConnectOptions.setKeepAliveInterval(90);
        return  mqttConnectOptions;
    }

    @Bean
    @Autowired
    public MqttClient mqttClient(MqttConnectOptions mqttConnectOptions, MqttSub mqttSub) throws MqttException {
        MqttClient client= new MqttClient(mqttPropConfig.getTcpHost(),mqttPropConfig.getClientId(),new MemoryPersistence());
        client.setCallback(mqttSub);
        //连接
        client.connect(mqttConnectOptions);
        List<String> subs=mqttPropConfig.getSubTopic();
        //订阅
        for(String sub:subs){
            client.subscribe(sub,0);
        }
        return client;
    }


}

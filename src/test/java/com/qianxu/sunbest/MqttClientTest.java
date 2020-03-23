package com.qianxu.sunbest;

import com.qianxu.sunbest.service.api.MqttService;
import org.eclipse.paho.client.mqttv3.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MqttClientTest {

    @Autowired
    MqttClient client;

    @Autowired
    MqttService mqttService;

    @Test
    public void pubTest(){
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(1);
            message.setRetained(true);
            message.setPayload("aaa".getBytes());
            MqttTopic mqttTopic = client.getTopic("aaa");
            MqttDeliveryToken token = mqttTopic.publish(message);//发布主题
            token.waitForCompletion();
        }catch (MqttPersistenceException e) {
            ;
        } catch (MqttException e) {
            ;
        }
    }

    @Test
    public void MqttServiceTest(){
        mqttService.sendMessage("aaa","sssssf");
    }
}

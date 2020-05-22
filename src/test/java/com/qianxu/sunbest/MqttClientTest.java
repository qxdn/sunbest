package com.qianxu.sunbest;

import com.qianxu.sunbest.model.MyMqttMessage;
import com.qianxu.sunbest.service.api.MqttService;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MqttClientTest {

    @Autowired
    MqttClient client;

    @Autowired
    MqttService mqttService;

    // @Test
    // public void pubTest(){
    //     try {
    //         MqttMessage message = new MqttMessage();
    //         message.setQos(1);
    //         message.setRetained(true);
    //         message.setPayload("aaaasc".getBytes());
    //         MqttTopic mqttTopic = client.getTopic("sunMsg");
    //         MqttDeliveryToken token = mqttTopic.publish(message);//发布主题
    //         token.waitForCompletion();
    //     }catch (MqttPersistenceException e) {
    //         ;
    //     } catch (MqttException e) {
    //         ;
    //     }
    // }
// 测试通过
//    @Test
//    public void messageTest(){
//        while(true){
//            MyMqttMessage message=mqttService.getMqttMessage();
//            if(message!=null){
//                System.out.println(message.toString());
//                log.info(message.toString());
//            }
//        }
//    }
}
package com.qianxu.sunbest.service.impl;

import com.qianxu.sunbest.component.MqttSub;
import com.qianxu.sunbest.config.MqttPropConfig;
import com.qianxu.sunbest.model.MyMqttMessage;
import com.qianxu.sunbest.service.api.MqttService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("mqttService")
public class MqttServiceImpl implements MqttService {

    @Autowired
    MqttClient client;

    @Autowired
    MqttPropConfig mqttPropConfig;

    @Autowired
    MqttSub mqttSub;

    @Override
    public void sendMessage(String msg) {
        List<String> pubs=mqttPropConfig.getPubTopic();
        for(String pub:pubs){
            sendMessage(pub,msg);
        }
    }

    @Override
    public void sendMessage(String topic, String msg) {
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(1);
            message.setRetained(true);
            message.setPayload(msg.getBytes());
            MqttTopic mqttTopic = client.getTopic(topic);
            MqttDeliveryToken token = mqttTopic.publish(message);//发布主题
            token.waitForCompletion();
        }catch (MqttPersistenceException e) {
           log.error(e.toString());
        } catch (MqttException e) {
            log.error(e.toString());
        }
    }

    @Override
    public void subscribe(String[] topic, int[] qos) {
        try {
            client.subscribe(topic,qos);
        } catch (MqttException e) {
            log.error(e.toString());
        }
    }

    @Override
    public void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            log.error(e.toString());
        }
    }

    @Override
    public void subscribe() {
        List<String> subs=mqttPropConfig.getSubTopic();
        for(String sub:subs){
            subscribe(sub,0);
        }
    }

    @Override
    public MyMqttMessage getMqttMessage() {
        return mqttSub.getMessage();
    }
}

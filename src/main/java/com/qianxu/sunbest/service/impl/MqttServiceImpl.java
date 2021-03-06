package com.qianxu.sunbest.service.impl;

import com.qianxu.sunbest.component.MqttSub;
import com.qianxu.sunbest.config.MqttPropConfig;
import com.qianxu.sunbest.model.MyMqttMessage;
import com.qianxu.sunbest.service.api.MqttService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
    public void sendMessage(String topic, String msg) throws MqttException {
        sendMessage(topic, msg, false);
    }

    @Override
    public void sendMessage(String topic, String msg, boolean retained) throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setRetained(true);
        message.setPayload(msg.getBytes());
        // 不保留
        message.setRetained(retained);
        MqttTopic mqttTopic = client.getTopic(topic);
        MqttDeliveryToken token = mqttTopic.publish(message);// 发布主题
        token.waitForCompletion();
    }

    @Override
    public void subscribe(String[] topic, int[] qos) {
        try {
            client.subscribe(topic, qos);
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
        List<String> subs = mqttPropConfig.getSubTopic();
        for (String sub : subs) {
            subscribe(sub, 0);
        }
    }

    @Nullable
    @Override
    public MyMqttMessage getMqttMessage() {
        return mqttSub.getMessage();
    }
}
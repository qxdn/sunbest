package com.qianxu.sunbest.service.api;

import com.qianxu.sunbest.model.MyMqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface MqttService {
    public void sendMessage(String topic,String msg) throws MqttException;
    public void subscribe(String[] topic,int[] qos);
    public void subscribe(String topic,int qos);
    public void subscribe();
    public MyMqttMessage getMqttMessage();
}
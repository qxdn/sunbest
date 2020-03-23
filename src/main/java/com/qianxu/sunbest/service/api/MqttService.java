package com.qianxu.sunbest.service.api;

import com.qianxu.sunbest.model.MyMqttMessage;

public interface MqttService {
    public void sendMessage(String msg);
    public void sendMessage(String topic,String msg);
    public void subscribe(String[] topic,int[] qos);
    public void subscribe(String topic,int qos);
    public void subscribe();
    public MyMqttMessage getMqttMessage();
}

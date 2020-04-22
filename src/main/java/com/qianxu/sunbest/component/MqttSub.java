package com.qianxu.sunbest.component;

import com.qianxu.sunbest.model.MyMqttMessage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
@Slf4j
public class MqttSub implements MqttCallback {

    static Queue<MyMqttMessage> queue=new LinkedList<MyMqttMessage>();

    @Override
    public void connectionLost(Throwable throwable) {
        log.info("Mqtt Connected Lost");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        log.info("Mqtt Message Arrived");
        queue.offer(new MyMqttMessage(s,mqttMessage.toString()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("Mqtt Message Delivery");
    }

    public MyMqttMessage getMessage(){
        return queue.poll();
    }
}
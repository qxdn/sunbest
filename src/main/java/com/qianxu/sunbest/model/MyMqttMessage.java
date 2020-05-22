package com.qianxu.sunbest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MyMqttMessage {
    private String topic;
    private String Message;
}
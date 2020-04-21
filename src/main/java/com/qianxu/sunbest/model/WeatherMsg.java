package com.qianxu.sunbest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherMsg {
    private Integer pm10;
    private Integer pm25;
    //降雨
    private Double pcpn;

}
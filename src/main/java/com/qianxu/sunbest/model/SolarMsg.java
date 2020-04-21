package com.qianxu.sunbest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SolarMsg {
    //时角
    private Double hour_angle;
    //太阳方位角
    private Double solar_azimuth_angle;
    //太阳高度角
    private Double solar_elevation_angle;
    //太阳时
    private Integer solar_hour;
}
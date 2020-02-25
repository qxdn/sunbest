package com.qianxu.sunbest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {
    //经纬度
    private Double lat;
    private Double lon;
    private Double userAngle;

    //最佳倾角
    private Double bestAngle;
    //节约照明电能
    private Double savePower;
    //预计发电量
    private Double expectPowerGeneration;

    //年平均发电
    private Double averagePowerGeneration;
    //开窗面积
    private Double area;

    //最佳发电效率
    private double[] bestPower;
    //当前发电效率
    private double[] currentPower;
}

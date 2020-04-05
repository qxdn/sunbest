package com.qianxu.sunbest.util;


public class AngleConvert {

    /**
     * 将倾角转换为坡度
     * @param angle 倾角 0~360
     * @return 坡度
     */
    public static Double convertAngle2Slope(Double angle){
        return Math.tan(angle*Math.PI/180);
    }
}
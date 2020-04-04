package com.qianxu.sunbest.service.api;

import com.qianxu.sunbest.model.SolarMsg;
import com.qianxu.sunbest.model.WeatherMsg;

public interface WeatherService {

    //获取天气
    public WeatherMsg getWeather(Double lat,Double lon);

    //维度 精度 海拔
    public SolarMsg getSolar(Double lat, Double lon, Integer alt);
}

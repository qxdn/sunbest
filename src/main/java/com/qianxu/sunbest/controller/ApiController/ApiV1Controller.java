package com.qianxu.sunbest.controller.ApiController;

import com.qianxu.sunbest.model.*;
import com.qianxu.sunbest.service.api.ModelService;
import com.qianxu.sunbest.service.api.MqttService;
import com.qianxu.sunbest.service.api.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ApiV1Controller {
    @Autowired
    ModelService modelService;

    @Autowired
    WeatherService weatherService;

    @Autowired
    MqttService mqttService;

    @PostMapping("/getAngle")
    public Answer designApi(UserDefine userDefine){
        return  modelService.getAnswer(userDefine);
    }

    @PostMapping("/BestAngle")
    public String getBestAngle(Predict predict){
        DecimalFormat df=new DecimalFormat("#0.00");
        Double angle= modelService.getBestAngle(predict);
        return df.format(angle);
    }

    @PostMapping("/mqttAngle")
    public String pubAngle(Double angle){
        try {
            mqttService.sendMessage(angle.toString());
        } catch (MqttException e) {
            log.error(e.toString());
            return "fail";
        }
        return "success";
    }

    @PostMapping("/Weather")
    public WeatherMsg getWeather(Double lat,Double lon){
        return weatherService.getWeather(lat,lon);
    }

    @PostMapping("/Solar")
    public SolarMsg getSolar(Double lat,Double lon,Integer alt){
        return weatherService.getSolar(lat,lon,alt);
    }
}

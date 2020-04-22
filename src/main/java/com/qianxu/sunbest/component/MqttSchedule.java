package com.qianxu.sunbest.component;

import com.qianxu.sunbest.model.MyMqttMessage;
import com.qianxu.sunbest.model.SolarMsg;
import com.qianxu.sunbest.service.api.MqttService;
import com.qianxu.sunbest.service.api.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqttSchedule {

    @Autowired
    MqttService mqttService;

    @Autowired
    WeatherService weatherService;

    //上次执行完2秒后再执行
    @Scheduled(fixedDelay = 2000)
    public void getMessage(){
        MyMqttMessage mqttMessage=mqttService.getMqttMessage();
        if(mqttMessage!=null){
            log.info("poll Not Null Message");
            String topic=mqttMessage.getTopic();
            String message=mqttMessage.getMessage();
            switch (topic){
                case "getSunMsg":handleGetSun(message);break;
                default:break;
            }
        }
    }

    private void handleGetSun(String message){
        try {
           String[] temp=message.split(",");
           Double lat=Double.parseDouble(temp[0]);
           Double lon=Double.parseDouble(temp[1]);
           Integer alt=Integer.parseInt(temp[2]);
           log.info("convert success");
           SolarMsg solarMsg=weatherService.getSolar(lat,lon,alt);
           log.info("get SunMsg success");
           //考虑目前数据简单 暂时不使用json格式
           String pubMessage=solarMsg.getSolar_elevation_angle()+","+solarMsg.getSolar_azimuth_angle();
           mqttService.sendMessage("sunMsg",pubMessage);
        }catch (Exception e){
            log.error("handleGetSun fail",e);
        }
    }
}

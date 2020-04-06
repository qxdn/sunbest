package com.qianxu.sunbest.controller.ApiController;

import com.qianxu.sunbest.model.*;
import com.qianxu.sunbest.service.api.ModelService;
import com.qianxu.sunbest.util.AngleConvert;

import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/getAngle")
    public Answer designApi(UserDefine userDefine){
        return  modelService.getAnswer(userDefine);
    }

    @PostMapping("/BestAngle")
    public String getBestAngle(Predict predict){
        DecimalFormat df=new DecimalFormat("#0.000");
        Double angle= modelService.getBestAngle(predict);
        log.debug("转换坡度");
        angle=AngleConvert.convertAngle2Slope(angle);
        return df.format(angle);
    }
}

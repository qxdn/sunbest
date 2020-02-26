package com.qianxu.sunbest.controller;

import com.qianxu.sunbest.model.Answer;
import com.qianxu.sunbest.model.Predict;
import com.qianxu.sunbest.model.UserDefine;
import com.qianxu.sunbest.service.api.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;


@Controller
@RequestMapping("/design")
public class DesignController {

    @Autowired
    ModelService modelService;

    @PostMapping("/handle")
    public ModelAndView submit(UserDefine userDefine){
        Answer answer=modelService.getAnswer(userDefine);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("result");
        mv.addObject("answer",answer);
        return mv;
    }

    @RequestMapping("/toDesign")
    public String toDesign(){
        return "design";
    }

    @PostMapping("/BestAngle")
    @ResponseBody
    public String getBestAngle(Predict predict){
        DecimalFormat df=new DecimalFormat("#0.00");
        Double angle= modelService.getBestAngle(predict);
        return df.format(angle);
    }


    @PostMapping("/api")
    @ResponseBody
    public Answer designApi(UserDefine userDefine){
        return  modelService.getAnswer(userDefine);
    }
}

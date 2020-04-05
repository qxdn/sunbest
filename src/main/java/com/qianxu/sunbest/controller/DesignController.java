package com.qianxu.sunbest.controller;

import com.qianxu.sunbest.model.Answer;
import com.qianxu.sunbest.model.UserDefine;
import com.qianxu.sunbest.service.api.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/design")
public class DesignController {

    @Autowired
    ModelService modelService;

    @PostMapping("/handle")
    public ModelAndView submit(UserDefine userDefine) {
        Answer answer = modelService.getAnswer(userDefine);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("answer", answer);
        //TODO: 这是瞎编的数据
        mv.addObject("windowsNumber", 2);
        Double area=userDefine.getHouseLength()*userDefine.getHouseWidth();
        mv.addObject("simulation", area);
        return mv;
    }

    @RequestMapping("/toDesign")
    public String toDesign() {
        return "design";
    }

    @RequestMapping("/showBuilding")
    public String showBuilding() {
        return "showbuilding";
    }

    @RequestMapping("/getMap/{lon}/{lat}")
    public ModelAndView getMap(@PathVariable("lon") Double lon,@PathVariable("lat") Double lat) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("baiduMap");
        mv.addObject("lon", lon);
        mv.addObject("lat", lat);
        return mv;
    }

}

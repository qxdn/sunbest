package com.qianxu.sunbest.controller;

import com.qianxu.sunbest.model.Answer;
import com.qianxu.sunbest.model.UserDefine;
import com.qianxu.sunbest.service.api.ModelService;
import com.qianxu.sunbest.util.AngleConvert;

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
        //将倾角转换为坡度
        answer.setBestAngle(AngleConvert.convertAngle2Slope(answer.getBestAngle()));

        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("answer", answer);
        
        //房屋面积
        Double houseArea = userDefine.getHouseLength() * userDefine.getHouseWidth();
        mv.addObject("simulation", houseArea);

        // TODO: 天窗个数 瞎编的
        int number = 0;
        if (answer.getArea() < 20) {
            number = 5;
        } else {
            number = 21;
        }
        mv.addObject("windowsNumber", number);
        // 天窗成本 真实数据
        mv.addObject("windowsCos", modelService.getWindowsCos(answer.getArea(), number));
        // 太阳能板成本
        mv.addObject("boardCos", modelService.getBoardCos(houseArea, answer.getArea()));
        //其他成本
        mv.addObject("otherCos", modelService.getOtherCos(number));
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
    public ModelAndView getMap(@PathVariable("lon") Double lon, @PathVariable("lat") Double lat) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("baiduMap");
        mv.addObject("lon", lon);
        mv.addObject("lat", lat);
        return mv;
    }

}

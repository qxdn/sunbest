package com.qianxu.sunbest.controller;

import com.qianxu.sunbest.model.Answer;
import com.qianxu.sunbest.model.UserDefine;
import com.qianxu.sunbest.service.api.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


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



}

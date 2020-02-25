package com.qianxu.sunbest.controller;


import com.qianxu.sunbest.model.Answer;
import com.qianxu.sunbest.model.UserDefine;
import com.qianxu.sunbest.service.api.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/design")
public class DesignController {

    @Autowired
    ModelService modelService;

    @PostMapping("/handle")
    @ResponseBody
    public Answer submit(UserDefine userDefine){
        Answer answer=modelService.getAnswer(userDefine);
        return answer;
    }

    @RequestMapping("/toDesign")
    public String toDesign(){
        return "design.html";
    }

}

package com.qianxu.sunbest.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/windows")
public class WindowsController {

    @RequestMapping("/window")
    public String windows(){
        log.debug("GOTO windows.html");
        return "windows";
    }
}

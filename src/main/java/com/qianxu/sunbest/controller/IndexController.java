package com.qianxu.sunbest.controller;

import java.util.ArrayList;
import java.util.List;

import com.qianxu.sunbest.model.Role;
import com.qianxu.sunbest.model.User;
import com.qianxu.sunbest.service.api.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/register")
    public ModelAndView register(@RequestParam("email") String email, @RequestParam("password") String password) {
        ModelAndView mv=new ModelAndView();

        if(userService.isUserExits(email)){
            mv.setViewName("register");
            mv.addObject("error", "email已经存在");
            return mv;
        }
        
        User user=new User();
        user.setEmail(email);
        user.setPassword(password);
        List<Role> roles=new ArrayList<>();
        Role role=new Role();
        role.setId(2);
        roles.add(role);
        user.setRoles(roles);
        int col= userService.addUser(user);
        if(col==0){
            mv.setViewName("register");
            mv.addObject("error", "注册失败");
            return mv;
        }
        mv.setViewName("index");
        return mv;
    }
}

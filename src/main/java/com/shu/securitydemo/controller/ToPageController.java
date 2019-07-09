package com.shu.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ToPageController {

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }
    @RequestMapping("home")
    public String home(){
        return "home";
    }
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("role")
    public String role(){
        return "pages/role";
    }
    @RequestMapping("toPage")
    public String toPage(String pagePath){
        return pagePath;
    }
}

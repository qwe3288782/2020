package com.yuxin.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    //warn
    @RequestMapping("/warn")
    public String warn(){
        return "warn";
    }
    //warn
    @RequestMapping("/test2")
    public String test2(){
        return "test2";
    }
    //warn
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}



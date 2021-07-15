package com.kang.ignite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class helloController {


    @RequestMapping("/say/hello")
    @ResponseBody
    public String hello(){
        return "Hello World" +
                "!";
    }
}

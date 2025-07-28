package com.example.uniSystem_web_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shared")
public class SharedController {

    @GetMapping("/")
    public String testing(){
        return "/student/studentUI";
    }

}
package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.security.CustomUserDetails;
import jdk.jshell.execution.StreamingExecutionControl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shared")
public class SharedController {

    @GetMapping("/showMyCourses")
    public String myCourses(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        boolean isStudent = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
//        boolean isDoctor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"));
//        if(isStudent){
//            model.addAttribute("userType" , "student");
//            model.addAttribute("student" , userDetails.getStudent());
//        }
//        else if(isDoctor){
//            model.addAttribute("userType" , "doctor");
//            model.addAttribute("doctor" , userDetails.getDoctor());
//        }
        return "/indices/shared/myCourses";
    }

}
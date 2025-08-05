package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    private Student getLoggedInStudent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getStudent();
    }

    private String getLoggedInStudentEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping("/studentUI")
    public String studentUI(Model model){
        Student student = getLoggedInStudent();
        model.addAttribute("student" , student);
        return "/indices/student/studentUI.html";
    }

    @GetMapping("/showPersonalInformation")
    public String personalInformation(Model model){
        Student student = getLoggedInStudent();
        model.addAttribute("student" , student);
        model.addAttribute("studentUsername" , getLoggedInStudentEmail());
        return "/indices/student/personalInformation";
    }

    @GetMapping("/showRegisterMenu")
    public String registerMenu(Model model){
        Student student = getLoggedInStudent();
        model.addAttribute("student" , student);
        return "/indices/student/courseRegistration";
    }

}
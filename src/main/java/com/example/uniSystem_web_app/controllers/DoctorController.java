package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private Doctor getLoggedInDoctor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getDoctor();
    }

    private String getLoggedInDoctorUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping("/doctorUI")
    public String doctorUI(Model model){
        Doctor doctor = getLoggedInDoctor();
        model.addAttribute("doctor" , doctor);
        return "/indices/doctor/doctorUI";
    }

    @GetMapping("/personalInformation")
    public String showDoctorPersonalInformation(Model model){
        Doctor doctor = getLoggedInDoctor();
        model.addAttribute("doctor" , doctor);
        model.addAttribute("doctorUsername" , getLoggedInDoctorUsername());
        return "/indices/doctor/personalInformation";
    }

    @GetMapping("attendanceForm")
    public String showAttendanceForm(Model model){
        Doctor doctor = getLoggedInDoctor();
        model.addAttribute("doctor" , doctor);
        return "/indices/doctor/attendanceForm";
    }

    @GetMapping("/attendance")
    public String showStudents(Model model){
        Doctor doctor = getLoggedInDoctor();
        model.addAttribute("doctor" , doctor);
        return "/indices/doctor/attendance";
    }
}

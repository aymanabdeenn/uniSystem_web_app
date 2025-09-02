package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.exceptions.DoctorNotFoundException;
import com.example.uniSystem_web_app.exceptions.StudentNotFoundException;
import com.example.uniSystem_web_app.repositories.DoctorRepository;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import com.example.uniSystem_web_app.security.CustomUserDetails;
import com.example.uniSystem_web_app.services.SectionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/shared")
public class SharedController {

    private final StudentRepository studentRepository;
    private final DoctorRepository doctorRepository;
    private final SectionService sectionService;

    public SharedController(StudentRepository studentRepository , DoctorRepository doctorRepository , SectionService sectionService){
        this.studentRepository = studentRepository;
        this.doctorRepository = doctorRepository;
        this.sectionService = sectionService;
    }

    @GetMapping("/showMyCourses")
    public String myCourses(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        boolean isStudent = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        boolean isDoctor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"));
        if(isStudent){
            Student student = studentRepository.findById(userDetails.getStudent().getId()).orElseThrow(() -> new StudentNotFoundException("The student does not exist."));
            model.addAttribute("userType" , "student");
            model.addAttribute("student" , student);
        }
        else if(isDoctor){
            Doctor doctor = doctorRepository.findById(userDetails.getDoctor().getId()).orElseThrow(() -> new DoctorNotFoundException("The doctor does not exist."));
            model.addAttribute("userType" , "doctor");
            model.addAttribute("doctor" , doctor);
        }
        return "/indices/shared/myCourses";
    }

    @GetMapping("/showCourse")
    public String showCourse(
            Model model
            , @RequestParam(name = "id") Long sectionId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Section section = sectionService.getSectionBySectionId(sectionId);

        boolean isStudent = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        boolean isDoctor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"));
        if(isStudent){
            Student student = studentRepository.findById(userDetails.getStudent().getId()).orElseThrow(() -> new StudentNotFoundException("The student does not exist."));
            model.addAttribute("userType" , "student");
            model.addAttribute("student" , student);
        }
        else if(isDoctor){
            Doctor doctor = doctorRepository.findById(userDetails.getDoctor().getId()).orElseThrow(() -> new DoctorNotFoundException("The doctor does not exist."));
            model.addAttribute("userType" , "doctor");
            model.addAttribute("doctor" , doctor);
        }
        model.addAttribute("section" , section);

        return "/indices/shared/course.html";
    }

    @GetMapping("/chatroom")
    public String showChatroom(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        boolean isStudent = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        boolean isDoctor = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"));
        if(isStudent){
            Student student = studentRepository.findById(userDetails.getStudent().getId()).orElseThrow(() -> new StudentNotFoundException("The student does not exist."));
            model.addAttribute("userType" , "student");
            model.addAttribute("student" , student);
        }
        else if(isDoctor){
            Doctor doctor = doctorRepository.findById(userDetails.getDoctor().getId()).orElseThrow(() -> new DoctorNotFoundException("The doctor does not exist."));
            model.addAttribute("userType" , "doctor");
            model.addAttribute("doctor" , doctor);
        }

        return "/indices/shared/AIChatroom.html";
    }
}
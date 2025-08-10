package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.exceptions.StudentNotFoundException;
import com.example.uniSystem_web_app.repositories.CourseRepository;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import com.example.uniSystem_web_app.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentController(CourseRepository courseRepository , StudentRepository studentRepository){
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    private Student getLoggedInStudent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Student student = userDetails.getStudent();
        return studentRepository.findById(student.getId()).orElseThrow(() -> new StudentNotFoundException("Student not found."));
    }

    private String getLoggedInStudentEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    public void addCourse(Student student , Course course){
        student.getCourses().add(course);
        course.getStudents().add(student);
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
    public String registerMenu(Model model , @RequestParam(required = false) String success , @RequestParam(required = false) String notFound , @RequestParam(required = false) String fullCap , @RequestParam(required = false) String alreadyRegistered){
        Student student = getLoggedInStudent();
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("student" , student);
        model.addAttribute("courses" , courses);
        if(success != null) model.addAttribute("success" , success);
        else if(notFound != null) model.addAttribute("notFound" , notFound);
        else if(fullCap != null) model.addAttribute("fullCap" , fullCap);
        else if(alreadyRegistered != null) model.addAttribute("alreadyRegistered" , alreadyRegistered);
        return "/indices/student/courseRegistration";
    }

    @PostMapping("/registerCourse")
    public String registerCourse(Model model , @RequestParam String courseNumber){
        Student student = getLoggedInStudent();
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("student" , student);
        model.addAttribute("courses" , courses);
        Course course = courseRepository.findByCourseId(courseNumber);
        if(course == null) return "redirect:/student/showRegisterMenu?notFound";
        if(course.getTakenSeats() >= course.getCapacity()) return "redirect:/student/showRegisterMenu?fullCap";
        for(Course COURSE : student.getCourses()){
            if(COURSE.getId() == course.getId()) return "redirect:/student/showRegisterMenu?alreadyRegistered";
        }
        synchronized (this){
            course.setTakenSeats(course.getTakenSeats() + 1);
            addCourse(student , course);
            studentRepository.save(student);
        }
        return "redirect:/student/showRegisterMenu?success";
    }

}
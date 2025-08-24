package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final StudentService studentService;
    private final DoctorService doctorService;
    private final CourseService courseService;
    private final SectionService sectionService;

    @Autowired
    public AdminController(AdminService adminService , StudentService studentService , DoctorService doctorService , CourseService courseService , SectionService sectionService){
        this.adminService = adminService;
        this.studentService = studentService;
        this.doctorService = doctorService;
        this.courseService = courseService;
        this.sectionService = sectionService;
    }

    @GetMapping("/adminUI")
    public String adminUI(Model model){
        return "/indices/admin/adminUI.html";
    }

    @GetMapping("/showAddStudent")
    public String showAddStudent(){
        return "/indices/admin/addStudent.html";
    }

    @GetMapping("/showAddDoctor")
    public String showAddDoctor() { return "/indices/admin/addDoctor.html"; }

    @GetMapping("/showCreateCourse")
    public String showCreateCourse() { return "/indices/admin/createCourse.html"; }

    @GetMapping("/showCreateSection")
    public String showCreateSection(Model model , @RequestParam(required = false) List<Course> courses) {
        if(courses != null) model.addAttribute("courses" , courses);
        return "/indices/admin/createSection.html";
    }

    @GetMapping("/showAssignDoctor")
    public String showAssignDoctor() { return "/indices/admin/assignDoctorToSection.html"; }

    @PostMapping("/addAStudent")
    public String addAStudent(
            Model model
            , @RequestParam String uniId
            , @RequestParam String name
            , @RequestParam String faculty
            , @RequestParam LocalDate dob
            , @RequestParam(name = "email") String username
            , @RequestParam String password
    ){
        //String uniId, String name, LocalDate dob , String faculty , String username , String password
        studentService.createNewStudent(uniId , name , dob , faculty , username , password);
        return "redirect:/admin/adminUI";
    }

    @PostMapping("/addADoctor")
    public String addADoctor(
            Model model
            , @RequestParam String uniId
            , @RequestParam String name
            , @RequestParam String faculty
            , @RequestParam LocalDate dob
            , @RequestParam(name = "email") String username
            , @RequestParam String password
    ){
        doctorService.createNewDoctor(uniId , name , dob , faculty , username , password);
        return "redirect:/admin/adminUI";
    }

    @PostMapping("/createACourse")
    public String createACourse(
            Model model
            , @RequestParam String name
            , @RequestParam(name = "courseCode") String courseId
            , @RequestParam String faculty
    ){
        courseService.createNewCourse(name , courseId , faculty);
        return "redirect:/admin/adminUI";
    }
    @PostMapping("/createASection")
    public String createASection(
            Model model
            , @RequestParam String courseId
            , @RequestParam int sectionNumber
            , @RequestParam LocalTime startTime
            , @RequestParam LocalTime endTime
            , @RequestParam int capacity
    ){
        Course course = courseService.getCourseByCourseId(courseId);
        sectionService.createNewSection(course , sectionNumber , startTime , endTime , capacity);
        return "redirect:/admin/adminUI";
    }

    @GetMapping("getCoursesForFaculty")
    public String getCoursesForFaculty(
            Model model
            , @RequestParam String faculty
    ){
        List<Course> courses = courseService.getCoursesByFaculty(faculty);
        model.addAttribute("courses" , courses);
        return "/indices/admin/createSection.html";
    }

    @PostMapping("/assignDoctor")
    public String assignDoctor(){
        return "redirect:/admin/adminUI";
    }

}

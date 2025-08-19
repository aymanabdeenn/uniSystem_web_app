package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.exceptions.StudentNotFoundException;
import com.example.uniSystem_web_app.repositories.CourseRepository;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import com.example.uniSystem_web_app.security.CustomUserDetails;
import com.example.uniSystem_web_app.services.CourseService;
import com.example.uniSystem_web_app.services.RecaptchaService;
import com.example.uniSystem_web_app.services.SectionService;
import com.example.uniSystem_web_app.services.StudentService;
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

    private final StudentService studentService;
    private final CourseService courseService;
    private final SectionService sectionService;
    private final RecaptchaService recaptchaService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentController(StudentService studentService , CourseService courseService, SectionService sectionService , RecaptchaService recaptchaService , CourseRepository courseRepository , StudentRepository studentRepository){
        this.studentService = studentService;
        this.courseService = courseService;
        this.sectionService = sectionService;
        this.recaptchaService = recaptchaService;
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

    public void addSection(Student student , Section section){
        student.getSections().add(section);
        section.getStudents().add(student);
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
    public String registerMenu(Model model , @RequestParam(required = false) String courseId , @RequestParam(required = false) String success , @RequestParam(required = false) String notFound , @RequestParam(required = false) String fullCap , @RequestParam(required = false) String alreadyRegistered, @RequestParam(required = false) String sectionNotFound , @RequestParam(required = false) String alreadyHasSectionWithinCourse, @RequestParam(required = false) String notValidated){
        Student student = getLoggedInStudent();
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("student" , student);
        model.addAttribute("courses" , courses);
        if(notValidated != null) model.addAttribute("notValidated" , notValidated);
        else if(success != null) model.addAttribute("success" , success);
        else if(notFound != null) model.addAttribute("notFound" , notFound);
        else if(fullCap != null) model.addAttribute("fullCap" , fullCap);
        else if(sectionNotFound != null) model.addAttribute("sectionNotFound" , sectionNotFound);
        else if(alreadyHasSectionWithinCourse != null) model.addAttribute("alreadyHasSectionWithinCourse" , alreadyHasSectionWithinCourse);
        else if(alreadyRegistered != null) model.addAttribute("alreadyRegistered" , alreadyRegistered);
        if(courseId != null) {
            Course course = courseService.getCourseByCourseId(courseId);
            model.addAttribute("sections" , course.getSections());
        }
        return "/indices/student/courseRegistration";
    }

    @GetMapping("/availableSections")
    public String availableSections(Model model , @RequestParam String courseId){
        return "redirect:/student/showRegisterMenu?courseId=" + courseId;
    }

    @PostMapping("/registerCourse")
    public String registerCourse(Model model , @RequestParam String courseCode , @RequestParam int sectionNumber , @RequestParam("g-recaptcha-response") String recaptchaToken){
        Student student = getLoggedInStudent();
        boolean isValid = recaptchaService.verifyToken(recaptchaToken);

        if(isValid){
            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("student" , student);
            model.addAttribute("courses" , courses);
            Course course = courseService.getCourseByCourseId(courseCode);
            if(course == null) return "redirect:/student/showRegisterMenu?notFound";

            Section section = sectionService.getCertainSectionFromACourseWithNumber(course , sectionNumber);
            if(section == null) return "redirect:/student/showRegisterMenu?sectionNotFound";
            if(student.hasSectionWithSameCourse(section)) return "redirect:/student/showRegisterMenu?alreadyHasSectionWithinCourse";
            if(section.getTakenSeats() >= section.getCapacity()) return "redirect:/student/showRegisterMenu?fullCap";
            if(studentService.doesStudentHasASection(student , section.getId())) return "redirect:/student/showRegisterMenu?alreadyRegistered";
            synchronized (this){
                section.setTakenSeats(section.getTakenSeats() + 1);
                addSection(student , section);
                studentRepository.save(student);
            }
            return "redirect:/student/showRegisterMenu?success";
        }
        else return "redirect:/student/showRegisterMenu?notValidated";
    }

}
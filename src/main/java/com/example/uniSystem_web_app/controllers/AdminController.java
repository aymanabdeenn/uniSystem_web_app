package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Faculty;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final StudentService studentService;
    private final DoctorService doctorService;
    private final CourseService courseService;
    private final SectionService sectionService;
    private final FacultyService facultyService;
    private final TimePeriodService timePeriodService;

    @Autowired
    public AdminController(AdminService adminService , StudentService studentService , DoctorService doctorService , CourseService courseService , SectionService sectionService , FacultyService facultyService , TimePeriodService timePeriodService){
        this.adminService = adminService;
        this.studentService = studentService;
        this.doctorService = doctorService;
        this.courseService = courseService;
        this.sectionService = sectionService;
        this.facultyService = facultyService;
        this.timePeriodService = timePeriodService;
    }

    @GetMapping("/adminUI")
    public String adminUI(
            Model model
            , @RequestParam(required = false) String nameEmpty
            , @RequestParam(required = false) String uniIdEmpty
            , @RequestParam(required = false) String emailEmpty
            , @RequestParam(required = false) String passwordEmpty
            , @RequestParam(required = false) String courseNameEmpty
            , @RequestParam(required = false) String courseIdEmpty
            , @RequestParam(required = false) String facultyEmpty
            , @RequestParam(required = false) String courseNotFound
            , @RequestParam(required = false) String timePeriodExists
            , @RequestParam(required = false) String success
    ){
        if(courseNameEmpty != null) model.addAttribute("courseNameEmpty" , courseNameEmpty);
        if(courseIdEmpty != null) model.addAttribute("courseIdEmpty" , courseIdEmpty);
        if(facultyEmpty != null) model.addAttribute("facultyEmpty" , facultyEmpty);
        if(nameEmpty != null) model.addAttribute("nameEmpty" , nameEmpty);
        if(uniIdEmpty != null) model.addAttribute("uniIdEmpty" , uniIdEmpty);
        if(emailEmpty != null) model.addAttribute("emailEmpty" , emailEmpty);
        if(passwordEmpty != null) model.addAttribute("passwordEmpty" , passwordEmpty);
        if(courseNotFound != null) model.addAttribute("courseNotFound" , courseNotFound);
        if(timePeriodExists != null) model.addAttribute("timePeriodExists" , timePeriodExists);
        if(success != null) model.addAttribute("success" , success);
        return "/indices/admin/adminUI.html";
    }

    @GetMapping("/showAddStudent")
    public String showAddStudent(Model model)
    {
        model.addAttribute("faculties" , facultyService.getAllFaculties());
        return "/indices/admin/addStudent.html";
    }

    @GetMapping("/showAddDoctor")
    public String showAddDoctor(Model model) {
        model.addAttribute("faculties" , facultyService.getAllFaculties());
        return "/indices/admin/addDoctor.html";
    }

    @GetMapping("/showCreateCourse")
    public String showCreateCourse(Model model) {
        model.addAttribute("faculties" , facultyService.getAllFaculties());
        return "/indices/admin/createCourse.html";
    }

    @GetMapping("/showCreateSection")
    public String showCreateSection(Model model , @RequestParam(required = false) List<Course> courses) {
        if(courses != null) model.addAttribute("courses" , courses);
        model.addAttribute("faculties" , facultyService.getAllFaculties());
        return "/indices/admin/createSection.html";
    }

    @GetMapping("/showAssignDoctor")
    public String showAssignDoctor(Model model) {
        model.addAttribute("faculties" , facultyService.getAllFaculties());
        return "/indices/admin/assignDoctorToSection.html";
    }

    @GetMapping("/showCreateTimePeriod")
    public String showCreateTimePeriod(Model model){
        return "/indices/admin/createTimePeriod.html";
    }

    @PostMapping("/addAStudent")
    public String addAStudent(
            Model model
            , @RequestParam String uniId
            , @RequestParam String name
            , @RequestParam(name = "faculty") Long facultyId
            , @RequestParam LocalDate dob
            , @RequestParam(name = "email") String username
    ){
        if(name.isEmpty()) return "redirect:/admin/adminUI?nameEmpty";
        if(uniId.isEmpty()) return "redirect:/admin/adminUI?uniIdEmpty";
        if(username.isEmpty()) return "redirect:/admin/adminUI?emailEmpty";
        studentService.createNewStudent(uniId , name , dob , facultyId , username);
        return "redirect:/admin/adminUI?success";
    }

    @PostMapping("/addADoctor")
    public String addADoctor(
            Model model
            , @RequestParam String uniId
            , @RequestParam String name
            , @RequestParam(name = "faculty") Long facultyId
            , @RequestParam LocalDate dob
            , @RequestParam(name = "email") String username
            , @RequestParam String password
    ){
        if(name.isEmpty()) return "redirect:/admin/adminUI?nameEmpty";
        if(uniId.isEmpty()) return "redirect:/admin/adminUI?uniIdEmpty";
        if(username.isEmpty()) return "redirect:/admin/adminUI?emailEmpty";
        if(password.isEmpty()) return "redirect:/admin/adminUI?passwordEmpty";
        doctorService.createNewDoctor(uniId , name , dob , facultyId , username , password);
        return "redirect:/admin/adminUI?success";
    }

    @PostMapping("/createACourse")
    public String createACourse(
            Model model
            , @RequestParam String name
            , @RequestParam(name = "courseCode") String courseId
            , @RequestParam(name = "faculty") Long facultyId
    ){
        if(name.isEmpty()) return "redirect:/admin/adminUI?courseNameEmpty";
        if(courseId.isEmpty()) return "redirect:/admin/adminUI?courseIdEmpty";
        courseService.createNewCourse(name , courseId , facultyId);
        return "redirect:/admin/adminUI?success";
    }
    @PostMapping("/createASection")
    public String createASection(
            Model model
            , @RequestParam String courseId
            , @RequestParam Long timePeriodId
            , @RequestParam int capacity
    ){
        if(courseId.isEmpty()) return "redirect:/admin/adminUI?courseNotFound";
        Course course = courseService.getCourseByCourseId(courseId);
        sectionService.createNewSection(course , timePeriodId , capacity);
        return "redirect:/admin/adminUI?success";
    }

    @GetMapping("getCoursesToCreateSection")
    public String getCoursesToCreateSection(
            Model model
            , @RequestParam(name = "faculty") Long facultyId
    ){
        List<Course> courses = courseService.getCoursesByFaculty(facultyId);
        model.addAttribute("courses" , courses);
        model.addAttribute("faculties" , facultyService.getAllFaculties());
        model.addAttribute("timePeriods" , timePeriodService.getAllTimePeriods());
        return "/indices/admin/createSection.html";
    }

    @PostMapping("/assignDoctor")
    public String assignDoctor(
            Model model
            , @RequestParam Long doctorId
            , @RequestParam Long sectionId
    ){
        adminService.assignDoctorToSection(doctorId , sectionId);
        return "redirect:/admin/adminUI?success";
    }

    @GetMapping("/getCoursesToAssign")
    public String getCoursesToAssign(
            Model model
            , @RequestParam(name = "faculty") Long facultyId
    ){
        Faculty faculty = facultyService.getFacultyById(facultyId);
        model.addAttribute("facultyId" , faculty.getId());
        model.addAttribute("courses" , faculty.getCourses());
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "/indices/admin/assignDoctorToSection.html";
    }

    @GetMapping("/getSectionsForCourse")
    public String getSectionsForCourse(
            Model model
            , @RequestParam(name = "facultyId") Long facultyId
            , @RequestParam String courseId
    ){
        Course course = courseService.getCourseByCourseId(courseId);
        Faculty faculty = facultyService.getFacultyById(facultyId);
        model.addAttribute("doctors" , faculty.getDoctors());
        model.addAttribute("sections" , course.getSections());
        model.addAttribute("faculties", facultyService.getAllFaculties());
        return "/indices/admin/assignDoctorToSection.html";
    }

    @PostMapping("/createATimePeriod")
    public String createATimePeriod(
            Model model
            , @RequestParam(name = "days") String daysCode
            , @RequestParam(name = "timePeriod") String timePeriodCode
    ){
        List<String> days = new ArrayList<String>();
        switch(daysCode){
            case "1" : {days.add("SUN"); days.add("TUE"); days.add("THU"); break;}
            case "2" : {days.add("MON"); days.add("WED"); break;}
        }

        LocalTime startTime = LocalTime.of(0 , 0 ,0) , endTime = LocalTime.of(0 , 0 ,0);
        switch(timePeriodCode){
            case "1": {startTime = LocalTime.of(9,0,0); endTime = LocalTime.of(10,0,0); break;}
            case "2": {startTime = LocalTime.of(10,0,0); endTime = LocalTime.of(11,0,0); break;}
            case "3": {startTime = LocalTime.of(11,0,0); endTime = LocalTime.of(12,0,0); break;}
            case "4": {startTime = LocalTime.of(12,0,0); endTime = LocalTime.of(13,0,0); break;}
            case "5": {startTime = LocalTime.of(13,0,0); endTime = LocalTime.of(14,0,0); break;}
            case "6": {startTime = LocalTime.of(14,0,0); endTime = LocalTime.of(15,0,0); break;}
            case "7": {startTime = LocalTime.of(15,0,0); endTime = LocalTime.of(16,0,0); break;}
            case "8": {startTime = LocalTime.of(8,30,0); endTime = LocalTime.of(10,0,0); break;}
            case "9": {startTime = LocalTime.of(10,0,0); endTime = LocalTime.of(11,30,0); break;}
            case "10": {startTime = LocalTime.of(11,30,0); endTime = LocalTime.of(13,0,0); break;}
            case "11": {startTime = LocalTime.of(13,0,0); endTime = LocalTime.of(14,30,0); break;}
            case "12": {startTime = LocalTime.of(14,30,0); endTime = LocalTime.of(16,0,0); break;}
        }

        boolean doesExist = timePeriodService.timePeriodExists(startTime , endTime , days);
        if(doesExist) return "redirect:/admin/adminUI?timePeriodExists";

        timePeriodService.createNewTimePeriod(startTime , endTime , days);
        return "redirect:/admin/adminUI?success";
    }

}

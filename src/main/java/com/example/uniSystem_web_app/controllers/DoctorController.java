package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.dto.AttendanceFormDTO;
import com.example.uniSystem_web_app.dto.StudentAttendanceDTO;
import com.example.uniSystem_web_app.entities.Attendance;
import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.exceptions.DoctorNotFoundException;
import com.example.uniSystem_web_app.repositories.DoctorRepository;
import com.example.uniSystem_web_app.security.CustomUserDetails;
import com.example.uniSystem_web_app.services.AttendanceService;
import com.example.uniSystem_web_app.services.CourseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final CourseService courseService;
    private final AttendanceService attendanceService;
    private final DoctorRepository doctorRepository;

    public DoctorController(CourseService courseService , AttendanceService attendanceService , DoctorRepository doctorRepository){
        this.courseService = courseService;
        this.attendanceService = attendanceService;
        this.doctorRepository = doctorRepository;
    }

    private Doctor getLoggedInDoctor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Doctor doctor = doctorRepository.findById(userDetails.getDoctor().getId()).orElseThrow(() -> new DoctorNotFoundException("The doctor does not exist."));
        return doctor;
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
    public String showStudents(Model model , @RequestParam String courseId , @RequestParam LocalDate date){
        Doctor doctor = getLoggedInDoctor();
        Course course = courseService.getCourseByCourseId(courseId);
        List<Student> courseStudents = courseService.getCourseStudents(course);

        AttendanceFormDTO attendanceFormDTO = new AttendanceFormDTO();
        for(Student student : courseStudents){
            StudentAttendanceDTO sa = new StudentAttendanceDTO();
            sa.setStudentId(student.getId());
            sa.setAbsent(false);
            attendanceFormDTO.getAttendances().add(sa);
        }

        model.addAttribute("date" , date);
        model.addAttribute("course" , course);
        model.addAttribute("doctor" , doctor);
        model.addAttribute("students" , courseStudents);
        model.addAttribute("attendanceFormDTO" , attendanceFormDTO);
        return "/indices/doctor/attendance";
    }

    @PostMapping("/takeAttendance")
    public String takeAttendance(@ModelAttribute AttendanceFormDTO attendanceFormDTO , @RequestParam String courseId , @RequestParam LocalDate date){
        List<StudentAttendanceDTO> students = attendanceFormDTO.getAttendances();
        for(StudentAttendanceDTO studentDTO : students){
            Attendance attendance = new Attendance(studentDTO.getStudentId() , courseId , date , studentDTO.getAbsent());
            attendanceService.addAttendance(attendance);
            System.out.println("--------------------------------------------");
            System.out.println(attendance.getCourseId() + " " + attendance.getStudentId() + " " + attendance.getAbsent() + " " + attendance.getDate());
        }
        return "redirect:/doctor/attendanceForm";
    }
}

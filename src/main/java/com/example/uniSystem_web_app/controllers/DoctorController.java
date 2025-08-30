package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.dto.AttendanceFormDTO;
import com.example.uniSystem_web_app.dto.NewPasswordDTO;
import com.example.uniSystem_web_app.dto.StudentAttendanceDTO;
import com.example.uniSystem_web_app.entities.*;
import com.example.uniSystem_web_app.exceptions.DoctorNotFoundException;
import com.example.uniSystem_web_app.repositories.DoctorRepository;
import com.example.uniSystem_web_app.security.CustomUserDetails;
import com.example.uniSystem_web_app.services.AccountService;
import com.example.uniSystem_web_app.services.AttendanceService;
import com.example.uniSystem_web_app.services.CourseService;
import com.example.uniSystem_web_app.services.SectionService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final CourseService courseService;
    private final SectionService sectionService;
    private final AttendanceService attendanceService;
    private final AccountService accountService;
    private final DoctorRepository doctorRepository;

    public DoctorController(CourseService courseService , SectionService sectionService , AttendanceService attendanceService , AccountService accountService , DoctorRepository doctorRepository){
        this.courseService = courseService;
        this.sectionService = sectionService;
        this.attendanceService = attendanceService;
        this.accountService = accountService;
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

    private Account getLoggedInAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return accountService.getAccountWithId(userDetails.getAccount().getId());
    }

    private boolean didLoggedInDoctorChangeInitPassword(){
        Account account = getLoggedInAccount();
        return account.getPasswordChanged();
    }

    public boolean isFutureDate(LocalDate date){
        return date.isAfter(LocalDate.now());
    }

    public boolean isMoreThanAWeekPast(LocalDate date){
        return Period.between(date , LocalDate.now()).getDays() > 7;
    }

    @GetMapping("/doctorUI")
    public String doctorUI(
            Model model
            , @RequestParam(required = false) String passwordChangeSuccess
            , @RequestParam(required = false) String passwordsDoNotMatch

    ){
        Doctor doctor = getLoggedInDoctor();
        model.addAttribute("doctor" , doctor);
        model.addAttribute("changedInitPassword" , didLoggedInDoctorChangeInitPassword());
        if(!didLoggedInDoctorChangeInitPassword()) model.addAttribute("newPasswordDTO" , new NewPasswordDTO());
        if(passwordChangeSuccess != null) model.addAttribute("passwordChangeSuccess" , passwordChangeSuccess);
        if(passwordsDoNotMatch != null) model.addAttribute("passwordsDoNotMatch" , passwordsDoNotMatch);
        return "/indices/doctor/doctorUI";
    }

    @GetMapping("/personalInformation")
    public String showDoctorPersonalInformation(Model model){
        Doctor doctor = getLoggedInDoctor();
        model.addAttribute("doctor" , doctor);
        model.addAttribute("doctorUsername" , getLoggedInDoctorUsername());
        return "/indices/doctor/personalInformation";
    }

    @GetMapping("/attendanceForm")
    public String showAttendanceForm(
            Model model,
            @RequestParam(required = false) boolean showSections,
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String future,
            @RequestParam(required = false) String moreThanAWeekPast) {

        Doctor doctor = getLoggedInDoctor();
        model.addAttribute("doctor", doctor);

        if (showSections && courseId != null) {
            Course course = courseService.getCourseByCourseId(courseId);
            model.addAttribute("courseId" , courseId);
            model.addAttribute("sections", course.getSections());
        }

        if (future != null) model.addAttribute("future", future);
        if (moreThanAWeekPast != null) model.addAttribute("moreThanAWeekPast", moreThanAWeekPast);

        model.addAttribute("courses", doctor.getCourses());
        return "/indices/doctor/attendanceForm";
    }

    @GetMapping("/showSections")
    public String showSections(Model model , @RequestParam String courseId){
        return "redirect:/doctor/attendanceForm?showSections=true&courseId=" + courseId;
    }

    @GetMapping("/attendance")
    public String showStudents(Model model , @RequestParam String courseId , @RequestParam(required = false) Long sectionId , @RequestParam LocalDate date){
        if(isFutureDate(date)) return "redirect:/doctor/attendanceForm?future";
        if(isMoreThanAWeekPast(date)) return "redirect:/doctor/attendanceForm?moreThanAWeekPast";
        Doctor doctor = getLoggedInDoctor();

        Course course = courseService.getCourseByCourseId(courseId);
        Section section = sectionService.getSectionBySectionId(sectionId);
        List<Student> sectionStudents = sectionService.getSectionStudents(section);

        List<Attendance> attendance = attendanceService.doesRecordInDateExist(date);
        AttendanceFormDTO attendanceFormDTO;
        if(attendance.size() == 0)  attendanceFormDTO = attendanceService.fillDtoWithDefaultValues(sectionStudents);
        else attendanceFormDTO = attendanceService.fillDtoWithRecords(attendance);

        model.addAttribute("date" , date);
        model.addAttribute("course" , course);
        model.addAttribute("section" , section);
        model.addAttribute("doctor" , doctor);
        model.addAttribute("students" , sectionStudents);
        model.addAttribute("attendanceFormDTO" , attendanceFormDTO);
        return "/indices/doctor/attendance";
    }

    @PostMapping("/takeAttendance")
    public String takeAttendance(@ModelAttribute AttendanceFormDTO attendanceFormDTO , @RequestParam String courseId , @RequestParam int sectionNumber , @RequestParam LocalDate date){
        List<StudentAttendanceDTO> students = attendanceFormDTO.getAttendances();
        List<Attendance> attendance = attendanceService.doesRecordInDateExist(date);

        if(attendance.size() == 0) attendanceService.addAttendanceList(attendanceFormDTO , courseId , sectionNumber , date);
        else attendanceService.modifyAttendanceList(attendanceFormDTO , attendance , courseId , date);

        return "redirect:/doctor/attendanceForm";
    }

    @PostMapping("/changeDoctorPassword")
    public String changeStudentPassword(@Valid NewPasswordDTO newPasswordDTO , BindingResult bindingResult , Model model){
        Account account = getLoggedInAccount();
        if(!bindingResult.hasErrors()){
            boolean changePassword = accountService.changePassword(account , newPasswordDTO);
            if(changePassword) return "redirect:/doctor/doctorUI?passwordChangeSuccess";
            else return "redirect:/doctor/doctorUI?passwordsDoNotMatch";
        }
        Doctor doctor = getLoggedInDoctor();
        model.addAttribute("doctor" , doctor);
        model.addAttribute("changedInitPassword" , didLoggedInDoctorChangeInitPassword());
        return "/indices/doctor/doctorUI.html";
    }
}

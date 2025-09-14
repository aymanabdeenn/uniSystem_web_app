package com.example.uniSystem_web_app.controllers;

import com.example.uniSystem_web_app.entities.*;
import com.example.uniSystem_web_app.exceptions.DoctorNotFoundException;
import com.example.uniSystem_web_app.exceptions.StudentNotFoundException;
import com.example.uniSystem_web_app.repositories.DoctorRepository;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import com.example.uniSystem_web_app.security.CustomUserDetails;
import com.example.uniSystem_web_app.services.AnnouncementFileService;
import com.example.uniSystem_web_app.services.AnnouncementService;
import com.example.uniSystem_web_app.services.SectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/shared")
public class SharedController {

    @Value("${files.upload.path}")
    String filesUploadPath;

    private final AnnouncementService announcementService;
    private final AnnouncementFileService announcementFileService;
    private final StudentRepository studentRepository;
    private final DoctorRepository doctorRepository;
    private final SectionService sectionService;

    public SharedController(AnnouncementService announcementService , AnnouncementFileService announcementFileService ,StudentRepository studentRepository , DoctorRepository doctorRepository , SectionService sectionService){
        this.announcementService = announcementService;
        this.announcementFileService = announcementFileService;
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
            , @RequestParam(required = false) String createAnnouncement
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
        if(createAnnouncement != null) model.addAttribute("createAnnouncement" , createAnnouncement);
        model.addAttribute("announcements" , announcementService.getAllAnnouncementsForSection(sectionId));
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

    @GetMapping("/showCreateAnnouncement")
    public String showCreateAnnouncement(Model model , @RequestParam(name = "id") Long sectionId){
        return "redirect:/shared/showCourse?id=" + sectionId + "&createAnnouncement";
    }

    @PostMapping("/createAnnouncement")
    public String createAnnouncement(
            Model model
            , @RequestParam String title
            , @RequestParam String content
            , @RequestParam MultipartFile[] files
            , @RequestParam Long sectionId
            , @RequestParam Long doctorId
    ){
      Announcement announcement = announcementService.createNewAnnouncement(title , content , sectionId , doctorId);



      if(files != null){
           for(MultipartFile file : files){
              try{
                  String uploadDir = filesUploadPath + sectionId + "/announcement_" + announcement.getId() + "/";
                  new File(uploadDir).mkdirs();

                  String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
                  file.transferTo(new File(filePath));

                  announcementFileService.createNewAnnouncementFile(announcement , file.getOriginalFilename() , filePath , file.getSize() , file.getContentType());
                }
               catch (Exception e){
                  e.printStackTrace();
                  return "Failed to upload file: " + file.getOriginalFilename();
                }
          }
        }
     return "redirect:/shared/showCourse?id=" + sectionId;
    }

    @GetMapping("/announcements/files/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        AnnouncementFile file = announcementFileService.getAnnouncementFileById(id);

        Path path = Paths.get(file.getPath()).toAbsolutePath().normalize();
        FileSystemResource resource = new FileSystemResource(path.toFile());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found or unreadable: " + file.getTitle());
        }

        // Encode filename to safely handle spaces, #, () etc.
        String encodedFileName = UriUtils.encode(file.getTitle(), StandardCharsets.UTF_8);

        // Use correct content type or fallback
        String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
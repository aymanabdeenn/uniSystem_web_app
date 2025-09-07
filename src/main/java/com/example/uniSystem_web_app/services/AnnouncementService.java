package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Announcement;
import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.exceptions.AnnouncementNotFoundException;
import com.example.uniSystem_web_app.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    private final DoctorService doctorService;
    private final SectionService sectionService;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementService(DoctorService doctorService , SectionService sectionService , AnnouncementRepository announcementRepository){
        this.doctorService = doctorService;
        this.sectionService = sectionService;
        this.announcementRepository = announcementRepository;
    }

    public Announcement getAnnouncementById(Long announcementId){
        return announcementRepository.findById(announcementId).orElseThrow(() -> new AnnouncementNotFoundException("Announcement not found."));
    }

    public List<Announcement> getAllAnnouncementsForSection(Long sectionId){
        return announcementRepository.findAllBySectionId(sectionId);
    }

    public Announcement createNewAnnouncement(String title , String content , Long sectionId , Long doctorId){
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Section section = sectionService.getSectionBySectionId(sectionId);
        Announcement announcement = new Announcement(title , content , doctor , section , LocalDateTime.now());
        announcementRepository.save(announcement);
        return announcement;
    }

}

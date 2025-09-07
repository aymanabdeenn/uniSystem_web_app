package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Announcement;
import com.example.uniSystem_web_app.entities.AnnouncementFile;
import com.example.uniSystem_web_app.exceptions.AnnouncementFileNotFoundException;
import com.example.uniSystem_web_app.repositories.AnnouncementFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementFileService {

    private final AnnouncementFileRepository announcementFileRepository;

    @Autowired
    public AnnouncementFileService(AnnouncementFileRepository announcementFileRepository){
        this.announcementFileRepository = announcementFileRepository;
    }

    public AnnouncementFile getAnnouncementFileById(Long id){
        return announcementFileRepository.findById(id).orElseThrow(() -> new AnnouncementFileNotFoundException("Announcement file not found."));
    }

    public void createNewAnnouncementFile(Announcement announcement , String fileName , String path , Long size, String contentType){
        AnnouncementFile file = new AnnouncementFile();
        file.setAnnouncement(announcement);
        file.setFileName(fileName);
        file.setPath(path);
        file.setSize(size);
        file.setContentType(contentType);
        announcementFileRepository.save(file);
    }

}

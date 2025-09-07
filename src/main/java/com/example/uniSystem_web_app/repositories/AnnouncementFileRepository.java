package com.example.uniSystem_web_app.repositories;

import com.example.uniSystem_web_app.entities.AnnouncementFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementFileRepository extends JpaRepository<AnnouncementFile, Long> {
}

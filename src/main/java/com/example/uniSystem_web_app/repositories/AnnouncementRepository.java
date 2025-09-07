package com.example.uniSystem_web_app.repositories;

import com.example.uniSystem_web_app.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    public List<Announcement> findAllBySectionId(Long sectionId);

}

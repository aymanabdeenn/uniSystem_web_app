package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy="announcement" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<AnnouncementFile> files = new ArrayList<>();

    public Announcement(){}

    public Announcement(String title, String content, Doctor doctor, Section section, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.doctor = doctor;
        this.section = section;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<AnnouncementFile> getFiles() {
        return files;
    }

    public void setFiles(List<AnnouncementFile> files) {
        this.files = files;
    }
}

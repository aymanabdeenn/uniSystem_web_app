package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Attendance {
    @Id
    @SequenceGenerator(
            name="attendance-sequence",
            sequenceName = "attendance-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "attendance-sequence"
    )
    private Long id;

    private Long studentId;
    private String courseId;
    private LocalDate date;
    private boolean absent;

    public Attendance(){}

    public Attendance(Long studentId , String courseId , LocalDate date , boolean absent){
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.absent = absent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean getAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }
}

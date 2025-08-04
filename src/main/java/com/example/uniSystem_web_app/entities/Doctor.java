package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Doctor {

    @Id
    @SequenceGenerator(
            name = "doctor-sequence",
            sequenceName = "doctor-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "doctor-sequence"
    )
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String uniId;

    @Column(
            nullable = false
    )
    private String name;
    private LocalDate dob;

    @Column(
            nullable=false
    )
    private String faculty;

    @OneToMany(
            mappedBy = "assignedDoctor",
            cascade = CascadeType.ALL
    )
    private List<Course> courses = new ArrayList<Course>();

    public Doctor(){}

    public Doctor(String uniId , String name , LocalDate dob, String faculty){
        this.uniId = uniId;
        this.name = name;
        this.dob = dob;
        this.faculty = faculty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniId() {
        return uniId;
    }

    public void setUniId(String uniId) {
        this.uniId = uniId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

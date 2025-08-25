package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private String code;

    @OneToMany(
            mappedBy = "faculty"
            , cascade = CascadeType.ALL
    )
    private List<Doctor> doctors = new ArrayList<Doctor>();

    @OneToMany(
            mappedBy = "faculty"
            , cascade = CascadeType.ALL
    )
    private List<Student> students = new ArrayList<Student>();

    @OneToMany(
            mappedBy = "faculty"
            , cascade = CascadeType.ALL
    )
    private List<Course> courses = new ArrayList<Course>();

    public Faculty(){}

    public Faculty(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

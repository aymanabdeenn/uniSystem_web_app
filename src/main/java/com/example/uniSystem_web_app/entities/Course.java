package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Course {

    @Id
    @SequenceGenerator(
            name = "course-sequence",
            sequenceName = "course-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
//            generator = "course-sequence"
    )
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String courseId;

    @Column(
            nullable = false
    )
    private String courseName;

    private String faculty;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<Section> sections = new ArrayList<>();

    public Course(){}

    public Course(String courseId, String courseName , String faculty) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.faculty =  faculty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}

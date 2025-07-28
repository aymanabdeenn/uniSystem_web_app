package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

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
            strategy = GenerationType.SEQUENCE,
            generator = "course-sequence"
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

    @ManyToOne
    @JoinColumn(name = "assigned_doctor_id")
    private Doctor assignedDoctor;

    @ManyToMany(
            mappedBy = "courses"
    )
    private List<Student> students;

    public Course(){}

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
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

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int numOfStudents(){
        return this.students.size();
    }
}

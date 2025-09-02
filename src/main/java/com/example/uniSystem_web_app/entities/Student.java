package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table
public class Student {

    @Id
    @SequenceGenerator(
            name="student-sequence",
            sequenceName = "student-sequence",
            allocationSize  =1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student-sequence"
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

    @Transient
    private int age;

    @ManyToOne
    @JoinColumn(name = "faculty")
    private Faculty faculty;

    @ManyToMany
    @JoinTable(
            name = "student_section",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private List<Section> sections = new ArrayList<Section>();

    public Student(){}

    public Student(String uniId, String name , LocalDate dob){
        this.uniId = uniId;
        this.name = name;
        this.dob = dob;
    }

    public Student(String uniId, String name, LocalDate dob , Faculty faculty) {
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

    public int getAge() {
        return Period.between(this.dob , LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addASection(Section section){
        this.sections.add(section);
    }

    public List<Course> getCourses(){
        List<Course> courses = new ArrayList<Course>();
        HashSet<Course> set = new HashSet<Course>();
        for(Section section : this.sections){
            if(!set.contains(section.getCourse())) courses.add(section.getCourse());
            set.add(section.getCourse());
        }
        return courses;
    }

    public boolean hasSectionWithSameCourse(Section section){
        for(Section SECTION : this.sections){
            if(SECTION.getCourse() == section.getCourse()) return true;
        }
        return false;
    }
}
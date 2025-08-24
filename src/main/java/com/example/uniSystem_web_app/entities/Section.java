package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table
public class Section {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private int sectionNumber;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "assigned_doctor_id")
    private Doctor assignedDoctor;

    @ManyToMany(
            mappedBy = "sections"
    )
    private List<Student> students;

    @Column(
            nullable=false
    )
    private int capacity;

    @Column(
            nullable = false
    )
    private int takenSeats = 0;

    private LocalTime startTime;
    private LocalTime endTime;

    public Section(){}

    public Section(Course course , int sectionNumber , LocalTime startTime , LocalTime endTime , int capacity){
        this.course = course;
        this.sectionNumber = sectionNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public Section(Course course , Doctor assignedDoctor , int capacity , int takenSeats , LocalTime startTime , LocalTime endTime){
        this.course = course;
        this.assignedDoctor = assignedDoctor;
        this.capacity = capacity;
        this.takenSeats = takenSeats;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public int getCapacity(){
        return this.capacity;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public int getTakenSeats() {
        return takenSeats;
    }

    public void setTakenSeats(int takenSeats) {
        this.takenSeats = takenSeats;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int numOfStudents(){
        return this.students.size();
    }

}

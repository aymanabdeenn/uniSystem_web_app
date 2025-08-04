package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Account {


    @Id
    @SequenceGenerator(
            name = "authentication-sequence",
            sequenceName = "authentication-name",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "authentication-sequence"
    )
    Long id;

    @Column(
            unique = true,
            nullable = false
    )
    String username;

    @Column(
            nullable = false
    )
    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "accounts_roles",
            joinColumns = {@JoinColumn(name = "account_id" , referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id" , referencedColumnName = "id")}
    )
    List<Role> roles = new ArrayList<Role>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Account(){}

    public Account(String username , String password){
        this.username = username;
        this.password = password;
    }

    public Account(String username , String password , Student student){
        this.username = username;
        this.password = password;
        this.student = student;
    }

    public Account(String username , String password , Doctor doctor){
        this.username = username;
        this.password = password;
        this.doctor = doctor;
    }

    public Account(String username , String password , Admin admin){
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}

package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Faculty;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.exceptions.FacultyNotFoundException;
import com.example.uniSystem_web_app.repositories.FacultyRepository;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StudentService {

    private final AccountCreationService acs;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(AccountCreationService acs , FacultyRepository facultyRepository , StudentRepository studentRepository , PasswordEncoder passwordEncoder){
        this.acs = acs;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerASection(Student student , Section section){
        student.addASection(section);
        studentRepository.save(student);
    }

    public boolean doesStudentHasASection(Student student, Long sectionId){
        for(Section section : student.getSections()){
            if(section.getId() == sectionId) return true;
        }
        return false;
    }

    //String uniId, String name, LocalDate dob , String faculty
    public Student createNewStudent(String uniId , String name , LocalDate dob, Long facultyId , String username , String password){
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new FacultyNotFoundException("Faculty not found"));
        Student student = new Student(uniId , name,  dob , faculty);
        acs.registerStudent(username , passwordEncoder.encode(password), student);
        return student;
    }
}
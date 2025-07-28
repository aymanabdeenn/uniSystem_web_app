package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void registerACourse(Student student , Course course){
        student.addACourse(course);
        studentRepository.save(student);
    }

}
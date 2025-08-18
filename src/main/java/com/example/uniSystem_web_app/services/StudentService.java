package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Section;
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

}
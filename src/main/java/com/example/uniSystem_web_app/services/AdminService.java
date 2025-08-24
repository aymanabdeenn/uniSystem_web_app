package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.repositories.AdminRepository;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository , StudentRepository studentRepository){
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
    }

}

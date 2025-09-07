package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Course;
import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.entities.Section;
import com.example.uniSystem_web_app.exceptions.DoctorNotFoundException;
import com.example.uniSystem_web_app.exceptions.SectionNotFoundException;
import com.example.uniSystem_web_app.repositories.AdminRepository;
import com.example.uniSystem_web_app.repositories.DoctorRepository;
import com.example.uniSystem_web_app.repositories.SectionRepository;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final DoctorRepository doctorRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository , StudentRepository studentRepository , DoctorRepository doctorRepository , SectionRepository sectionRepository){
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.doctorRepository = doctorRepository;
        this.sectionRepository = sectionRepository;
    }

    public boolean assignDoctorToSection(Long doctorId , Long sectionId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found."));
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new SectionNotFoundException("Section not found."));
        for(Section SECTION : doctor.getSections()) if(SECTION.getId() == sectionId) return false;
        section.setAssignedDoctor(doctor);
        doctor.getSections().add(section);
        doctorRepository.save(doctor);
        return true;
    }

}

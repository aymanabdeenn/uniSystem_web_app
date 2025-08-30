package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.entities.Faculty;
import com.example.uniSystem_web_app.exceptions.FacultyNotFoundException;
import com.example.uniSystem_web_app.repositories.DoctorRepository;
import com.example.uniSystem_web_app.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DoctorService {

    private final AccountCreationService acs;
    private final FacultyRepository facultyRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorService( AccountCreationService acs , FacultyRepository facultyRepository ,DoctorRepository doctorRepository , PasswordEncoder passwordEncoder){
        this.acs = acs;
        this.facultyRepository = facultyRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Doctor createNewDoctor(String uniId, String name, LocalDate dob , Long facultyId , String username){
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new FacultyNotFoundException("Faculty not found"));
        Doctor doctor = new Doctor(uniId , name , dob , faculty);
        acs.registerDoctor(username , passwordEncoder.encode(name+username) , doctor);
        return doctor;
    }

}

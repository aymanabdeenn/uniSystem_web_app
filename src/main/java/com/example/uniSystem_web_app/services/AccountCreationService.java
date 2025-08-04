package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.*;
import com.example.uniSystem_web_app.exceptions.RoleDoesNotExistException;
import com.example.uniSystem_web_app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationService {

    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    private final DoctorRepository doctorRepository;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountCreationService(AccountRepository accountRepository , StudentRepository studentRepository , DoctorRepository doctorRepository , AdminRepository adminRepository , RoleRepository roleRepository){
        this.accountRepository = accountRepository;
        this.studentRepository = studentRepository;
        this.doctorRepository = doctorRepository;
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
    }

    public void registerStudent(String username , String password , Student student){
        Account account = new Account(username , password , student);
        Role studentRole = roleRepository.findById(1L).orElseThrow(() -> new RoleDoesNotExistException("The role does not exist!"));
        account.getRoles().add(studentRole);
        accountRepository.save(account);
        studentRepository.save(student);
    }

    public void registerDoctor(String username , String password , Doctor doctor){
        Account account = new Account(username , password , doctor);
        Role doctorRole = roleRepository.findById(2L).orElseThrow(() -> new RoleDoesNotExistException("The role does not exist!"));
        account.getRoles().add(doctorRole);
        accountRepository.save(account);
        doctorRepository.save(doctor);
    }

    public void registerAdmin(String username , String password , Admin admin){
        Account account = new Account(username , password, admin);
        Role adminRole = roleRepository.findById(3L).orElseThrow(() -> new RoleDoesNotExistException("The role does not exist!"));
        account.getRoles().add(adminRole);
        accountRepository.save(account);
        adminRepository.save(admin);
    }
}
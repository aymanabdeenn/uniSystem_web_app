package com.example.uniSystem_web_app.configurations;

import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.repositories.AccountRepository;
import com.example.uniSystem_web_app.repositories.StudentRepository;
import com.example.uniSystem_web_app.services.AccountCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    private final StudentRepository studentRepository;
    private final AccountRepository accountRepository;
    private final AccountCreationService acs;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentConfig(StudentRepository studentRepository , AccountRepository accountRepository , AccountCreationService acs , PasswordEncoder passwordEncoder){
        this.studentRepository = studentRepository;
        this.accountRepository = accountRepository;
        this.acs = acs;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunnerStudents(){
        return args -> {
            //String uniId, String name, LocalDate dob , String faculty
            Student student1 = new Student("123456" , "Std1" , LocalDate.of(2000 , Month.JANUARY , 2) , "CPE");
            Student student2 = new Student("123457" , "Std2" , LocalDate.of(2010 , Month.MAY , 2) , "CS");

            acs.registerStudent("std1@uni.com" , passwordEncoder.encode("123") , student1);
            acs.registerStudent("std2@uni.com" , passwordEncoder.encode("123") , student2);
        };
    }
}

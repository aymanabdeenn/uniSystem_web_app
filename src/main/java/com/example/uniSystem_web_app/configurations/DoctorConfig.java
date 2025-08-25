package com.example.uniSystem_web_app.configurations;

import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.entities.Faculty;
import com.example.uniSystem_web_app.repositories.FacultyRepository;
import com.example.uniSystem_web_app.services.AccountCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class DoctorConfig {

    private final FacultyRepository facultyRepository;

    private final AccountCreationService acs;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorConfig(FacultyRepository facultyRepository, AccountCreationService acs , PasswordEncoder passwordEncoder){
        this.facultyRepository = facultyRepository;
        this.acs = acs;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunnerDoctors(){
        return args -> {
            Faculty faculty1 = facultyRepository.findByCode("CPE");
            Faculty faculty2 = facultyRepository.findByCode("CS");
            Doctor doctor1 = new Doctor("123456" , "Doc1" , LocalDate.of(1970 , Month.JANUARY , 5) , faculty1);
            Doctor doctor2 = new Doctor("123457" , "Doc2" , LocalDate.of(1965 , Month.MAY , 11) , faculty2);

            acs.registerDoctor("doc1@uni.com" , passwordEncoder.encode("123") , doctor1);
            acs.registerDoctor("doc2@uni.com" , passwordEncoder.encode("123") , doctor2);
        };
    }

}

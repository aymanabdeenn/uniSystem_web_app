package com.example.uniSystem_web_app.configurations;

import com.example.uniSystem_web_app.entities.Doctor;
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

    private final AccountCreationService acs;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorConfig(AccountCreationService acs , PasswordEncoder passwordEncoder){
        this.acs = acs;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunnerDoctors(){
        return args -> {
            //String uniId , String name , LocalDate dob, String faculty
            Doctor doctor1 = new Doctor("123456" , "Doc1" , LocalDate.of(1970 , Month.JANUARY , 5) , "CPE");
            Doctor doctor2 = new Doctor("123457" , "Doc2" , LocalDate.of(1965 , Month.MAY , 11) , "CS");

            acs.registerDoctor("doc1@uni.com" , passwordEncoder.encode("123") , doctor1);
            acs.registerDoctor("doc2@uni.com" , passwordEncoder.encode("123") , doctor2);
        };
    }

}

package com.example.uniSystem_web_app.configurations;

import com.example.uniSystem_web_app.entities.Faculty;
import com.example.uniSystem_web_app.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class FacultyConfig {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyConfig(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }


    @Bean
    @Order(2)
    CommandLineRunner commandLineRunner(){
        return args -> {
            Faculty faculty1 = new Faculty("Computer Engineering" , "CPE");
            Faculty faculty2 = new Faculty("Computer science" , "CS");
            facultyRepository.saveAll(List.of(faculty1 , faculty2));
        };

    }}

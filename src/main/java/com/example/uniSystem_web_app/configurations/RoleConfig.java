package com.example.uniSystem_web_app.configurations;

import com.example.uniSystem_web_app.entities.Role;
import com.example.uniSystem_web_app.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class RoleConfig {

    private final RoleRepository roleRepository;

    public RoleConfig(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Bean
    @Order(1)
    CommandLineRunner commandLineRunnerRoles(){
        return args -> {
            Role studentRole = new Role("ROLE_STUDENT");
            Role doctorRole = new Role("ROLE_DOCTOR");
            Role adminRole = new Role("ROLE_ADMIN");

            roleRepository.saveAll(List.of(studentRole , doctorRole , adminRole));
        };
    }

}

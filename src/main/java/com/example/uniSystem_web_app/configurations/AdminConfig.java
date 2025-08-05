package com.example.uniSystem_web_app.configurations;

import com.example.uniSystem_web_app.entities.Admin;
import com.example.uniSystem_web_app.services.AccountCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfig {

    private final AccountCreationService acs;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminConfig(AccountCreationService acs , PasswordEncoder passwordEncoder){
        this.acs = acs;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunnerAdmins(){
        return args -> {
            Admin admin1 = new Admin("admin1");
            acs.registerAdmin("adm1" , "123" , admin1);
        };
    }
}
package com.example.uniSystem_web_app.configurations;

import com.example.uniSystem_web_app.entities.Admin;
import com.example.uniSystem_web_app.services.AccountCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {

    private final AccountCreationService acs;

    @Autowired
    public AdminConfig(AccountCreationService acs){
        this.acs = acs;
    }

    @Bean
    CommandLineRunner commandLineRunnerAdmins(){
        return args -> {
            Admin admin1 = new Admin("admin1");
            acs.registerAdmin("adm1" , "123" , admin1);
        };
    }
}

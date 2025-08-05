package com.example.uniSystem_web_app.security;

import com.example.uniSystem_web_app.entities.Account;
import com.example.uniSystem_web_app.entities.Admin;
import com.example.uniSystem_web_app.entities.Doctor;
import com.example.uniSystem_web_app.entities.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Account account;

    public CustomUserDetails(Account account){
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.account.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword(){
        return this.account.getPassword();
    }

    @Override
    public String getUsername(){
        return this.account.getUsername();
    }

    public Student getStudent(){
        return this.account.getStudent();
    }

    public Doctor getDoctor(){
        return this.account.getDoctor();
    }

    public Admin getAdmin(){
        return this.account.getAdmin();
    }
}

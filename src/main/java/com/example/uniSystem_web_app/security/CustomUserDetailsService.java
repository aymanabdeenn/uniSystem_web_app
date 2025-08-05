package com.example.uniSystem_web_app.security;


import com.example.uniSystem_web_app.entities.Account;
import com.example.uniSystem_web_app.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public CustomUserDetailsService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Account account = accountRepository.findFirstByUsername(username);
        if(account == null) throw new UsernameNotFoundException("Username " + username + " does not exist!");
        return new CustomUserDetails(account);
    }
}

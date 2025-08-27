package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.dto.NewPasswordDTO;
import com.example.uniSystem_web_app.entities.Account;
import com.example.uniSystem_web_app.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.uniSystem_web_app.exceptions.AccountNotFoundException;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository , PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account getAccountWithId(Long id){
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found."));
    }

    @Transactional
    public boolean changePassword(Account account , NewPasswordDTO newPasswordDTO){
        if(!newPasswordDTO.getNewPassword().equals(newPasswordDTO.getRePassword())) return false;
        account.setPassword(passwordEncoder.encode(newPasswordDTO.getNewPassword()));
        account.setPasswordChanged(true);
        accountRepository.save(account);
        return true;
    }
}

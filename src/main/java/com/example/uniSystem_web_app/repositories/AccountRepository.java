package com.example.uniSystem_web_app.repositories;

import com.example.uniSystem_web_app.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account , Long> {

    public Optional<Account> findByUsername(String username);

    public Account findFirstByUsername(String username);
}

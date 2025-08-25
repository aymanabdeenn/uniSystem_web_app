package com.example.uniSystem_web_app.repositories;

import com.example.uniSystem_web_app.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByCode(String code);
}

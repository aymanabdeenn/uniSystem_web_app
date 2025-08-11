package com.example.uniSystem_web_app.repositories;

import com.example.uniSystem_web_app.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    public List<Attendance> findAllByDate(LocalDate date);

}

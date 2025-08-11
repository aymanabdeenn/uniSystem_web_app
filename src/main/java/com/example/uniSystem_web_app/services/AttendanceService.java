package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.entities.Attendance;
import com.example.uniSystem_web_app.repositories.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository){
        this.attendanceRepository = attendanceRepository;
    }

    public void addAttendance(Attendance attendance){
        attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllByDate(LocalDate date){
        return attendanceRepository.findAllByDate(date);
    }

}

package com.example.uniSystem_web_app.dto;

import java.util.ArrayList;
import java.util.List;

public class AttendanceFormDTO {

    private List<StudentAttendanceDTO> attendances = new ArrayList<>();

    public List<StudentAttendanceDTO> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<StudentAttendanceDTO> attendances) {
        this.attendances = attendances;
    }
}

package com.example.uniSystem_web_app.dto;

public class StudentAttendanceDTO {

    private Long studentId;
    private Boolean absent;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Boolean getAbsent() {
        return absent;
    }

    public void setAbsent(Boolean absent) {
        this.absent = absent;
    }
}

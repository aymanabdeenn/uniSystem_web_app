package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.dto.AttendanceFormDTO;
import com.example.uniSystem_web_app.dto.StudentAttendanceDTO;
import com.example.uniSystem_web_app.entities.Attendance;
import com.example.uniSystem_web_app.entities.Student;
import com.example.uniSystem_web_app.exceptions.StudentNotFoundException;
import com.example.uniSystem_web_app.repositories.AttendanceRepository;
import jakarta.transaction.Transactional;
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

    public List<Attendance> doesRecordInDateExist(LocalDate date){
        return attendanceRepository.findByDate(date);
    }

    public AttendanceFormDTO fillDtoWithRecords(List<Attendance> attendance) {
        AttendanceFormDTO attendanceFormDTO = new AttendanceFormDTO();
        for(Attendance attendant : attendance){
            StudentAttendanceDTO sa = new StudentAttendanceDTO();
            sa.setStudentId(attendant.getStudentId());
            sa.setAbsent(attendant.getAbsent());
            attendanceFormDTO.getAttendances().add(sa);
        }
        return attendanceFormDTO;
    }

    public AttendanceFormDTO fillDtoWithDefaultValues(List<Student> courseStudents){
        AttendanceFormDTO attendanceFormDTO = new AttendanceFormDTO();
        for(Student student : courseStudents){
            StudentAttendanceDTO sa = new StudentAttendanceDTO();
            sa.setStudentId(student.getId());
            sa.setAbsent(false);
            attendanceFormDTO.getAttendances().add(sa);
        }
        return attendanceFormDTO;
    }

    @Transactional
    public void modifyRecord(Attendance attendant , Long studentId , Boolean absent){
        attendant.setStudentId(studentId);
        attendant.setAbsent(absent);
        attendanceRepository.save(attendant);
    }

    public void addAttendanceList(AttendanceFormDTO attendanceFormDTO , String courseId , LocalDate date){
        for(StudentAttendanceDTO sa : attendanceFormDTO.getAttendances()){
            Attendance attendant = new Attendance(sa.getStudentId() , courseId , date , sa.getAbsent());
            addAttendance(attendant);
            System.out.println("----------------------ADD----------------------");
            System.out.println(attendant.getCourseId() + " " + attendant.getStudentId() + " " + attendant.getAbsent() + " " + attendant.getDate());
        }
    }

    public void modifyAttendanceList(AttendanceFormDTO attendanceFormDTO , List<Attendance> attendance, String courseId ,LocalDate date){
        List<StudentAttendanceDTO> studentsDTO = attendanceFormDTO.getAttendances();
        for(int i = 0 ; i < attendance.size() ; i++){
            modifyRecord(attendance.get(i) , studentsDTO.get(i).getStudentId() , studentsDTO.get(i).getAbsent());
            System.out.println("----------------------MODIFY----------------------");
            System.out.println(attendance.get(i).getCourseId() + " " + attendance.get(i).getStudentId() + " " + attendance.get(i).getAbsent() + " " + attendance.get(i).getDate());
        }
    }
}
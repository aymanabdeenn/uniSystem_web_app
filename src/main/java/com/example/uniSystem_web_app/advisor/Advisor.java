package com.example.uniSystem_web_app.advisor;

import com.example.uniSystem_web_app.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class Advisor {

    @ExceptionHandler(RoleDoesNotExistException.class)
    public void roleDoesNotExist(RoleDoesNotExistException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public void studentDoesNotExist(StudentNotFoundException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public void DoctorDoesNotExist(DoctorNotFoundException ex){
        System.out.println(ex.getMessage());
    }
    @ExceptionHandler(SectionNotFoundException.class)
    public void SectionDoesNotExist(SectionNotFoundException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    public void FacultyNotFound(FacultyNotFoundException ex){
        System.out.println(ex.getMessage());
    }

}

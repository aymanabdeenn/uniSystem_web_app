package com.example.uniSystem_web_app.advisor;

import com.example.uniSystem_web_app.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    public void doctorDoesNotExist(DoctorNotFoundException ex){
        System.out.println(ex.getMessage());
    }
    @ExceptionHandler(SectionNotFoundException.class)
    public void sectionDoesNotExist(SectionNotFoundException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    public void facultyNotFound(FacultyNotFoundException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(TimePeriodNotFoundException.class)
    public void timePeriodNotFound(TimePeriodNotFoundException ex){
        System.out.println(ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public void accountNotFound(AccountNotFoundException ex) {
        System.out.println(ex.getMessage());
    }

}

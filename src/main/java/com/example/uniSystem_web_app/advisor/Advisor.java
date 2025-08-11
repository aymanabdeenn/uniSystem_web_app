package com.example.uniSystem_web_app.advisor;

import com.example.uniSystem_web_app.exceptions.DoctorNotFoundException;
import com.example.uniSystem_web_app.exceptions.RoleDoesNotExistException;
import com.example.uniSystem_web_app.exceptions.StudentNotFoundException;
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
    public void DoctorDoesNotExist(DoctorNotFoundException ex){
        System.out.println(ex.getMessage());
    }

}

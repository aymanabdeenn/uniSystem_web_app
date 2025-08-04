package com.example.uniSystem_web_app.advisor;

import com.example.uniSystem_web_app.exceptions.RoleDoesNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advisor {

    @ExceptionHandler(RoleDoesNotExistException.class)
    public void roleDoesNotExist(RoleDoesNotExistException ex){
        System.out.println(ex.getMessage());
    }

}

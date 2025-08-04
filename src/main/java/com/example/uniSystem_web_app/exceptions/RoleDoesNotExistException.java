package com.example.uniSystem_web_app.exceptions;

public class RoleDoesNotExistException extends RuntimeException {
    public RoleDoesNotExistException(String message) {
        super(message);
    }
}

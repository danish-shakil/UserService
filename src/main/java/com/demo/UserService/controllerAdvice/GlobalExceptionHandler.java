package com.demo.UserService.controllerAdvice;

import com.demo.UserService.exception.UserAlreadyExistsException;
import com.demo.UserService.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("TimeStamp", LocalDateTime.now());
        response.put("Status", HttpStatus.NOT_FOUND.value());
        response.put("Error", "User not found");
        response.put("Message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserNotFound(UserAlreadyExistsException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("TimeStamp", LocalDateTime.now());
        response.put("Status", HttpStatus.CONFLICT.value());
        response.put("Error", "User already exists");
        response.put("Message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}

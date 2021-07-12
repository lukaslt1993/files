package com.github.lukaslt1993.files.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = FileReadException.class)
    public ResponseEntity<Object> handleFileReadException(FileReadException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

}

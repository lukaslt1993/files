package com.github.lukaslt1993.files.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileReadException extends FileException {

    private final HttpStatus status;

    public FileReadException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public FileReadException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
    
}

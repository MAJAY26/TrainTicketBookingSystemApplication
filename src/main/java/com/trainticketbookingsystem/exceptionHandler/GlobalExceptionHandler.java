package com.trainticketbookingsystem.exceptionHandler;


import com.trainticketbookingsystem.exception.InvalidSectionException;
import com.trainticketbookingsystem.exception.NoUserFoundInTheSectionException;
import com.trainticketbookingsystem.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoUserFoundInTheSectionException.class)
    public ResponseEntity<String> handleNoUserFoundInTheSectionException(NoUserFoundInTheSectionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidSectionException.class)
    public ResponseEntity<String> handleInvalidSectionException(InvalidSectionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}


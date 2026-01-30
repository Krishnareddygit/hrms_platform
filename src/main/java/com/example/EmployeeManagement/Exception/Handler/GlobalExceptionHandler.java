package com.example.EmployeeManagement.Exception.Handler;


import com.example.EmployeeManagement.DTO.ApiDto;
import com.example.EmployeeManagement.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiDto> handleEmployeeNotFound(EmployeeNotFoundException ex){
        ApiDto error = new ApiDto(
          404,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmployeePersonalNotFoundException.class)
    public ResponseEntity<ApiDto> handleEmployeePersonalNotFound(EmployeePersonalNotFoundException ex){
        ApiDto error = new ApiDto(
                404,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmployeePersonalExistsException.class)
    public ResponseEntity<ApiDto> handleEmployeePersonalExistsException(EmployeePersonalExistsException ex){
        ApiDto error = new ApiDto(
                409,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmployeeEducationNotFoundException.class)
    public ResponseEntity<ApiDto> handleEmployeeEducationNotExist(EmployeeEducationNotFoundException ex){
        ApiDto error = new ApiDto(
                404,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiDto> handleDataNotFoundException(DataNotFoundException ex){
        ApiDto error = new ApiDto(
                404,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}

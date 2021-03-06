package com.example.demo.exceptionHandler;

import com.example.demo.exceptions.ItemNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(){
        return ResponseEntity.badRequest().body(ExceptionResponse.builder()
                .time(java.util.Calendar.getInstance().getTime().toString())
                .error("something went wrong! 400 :<")
                .message(":(")
                .build());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleItemNotFoundException(){
        return ResponseEntity.badRequest().body(ExceptionResponse.builder()
                .time(java.util.Calendar.getInstance().getTime().toString())
                .error("item not found! 404 :(")
                .message(":(")
                .build());
    }
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(ExceptionResponse.builder()
                .time(java.util.Calendar.getInstance().getTime().toString())
                .error("Type mismatch! 400 :(")
                .message(":(")
                .build());
    }
}

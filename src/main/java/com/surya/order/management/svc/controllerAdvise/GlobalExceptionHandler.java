package com.surya.order.management.svc.controllerAdvise;

import com.surya.order.management.svc.excpetion.InvalidInputException;
import com.surya.order.management.svc.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> InvalidInputException(InvalidInputException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.toString(), e.getMessage()));
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorResponse> connectException(ConnectException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage()));
    }
}

package com.kevin.prayerappservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(HttpServletRequest request, Exception exception){
        Error error = Error.builder()
                .errorCode(ErrorCode.GENERIC_ERROR.getErrCode())
                .message(ErrorCode.GENERIC_ERROR.getErrMessageKey())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Error> handleValidationException(HttpServletRequest request, DataIntegrityViolationException exception){
        System.out.println(exception.getCause().getLocalizedMessage());
        Error error = Error.builder()
                .errorCode(ErrorCode.DATA_VALIDATION_ERROR.getErrCode())
                .message(exception.getMostSpecificCause().getLocalizedMessage())
                .reqMethod(request.getMethod())
                .url(request.getRequestURL().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



}

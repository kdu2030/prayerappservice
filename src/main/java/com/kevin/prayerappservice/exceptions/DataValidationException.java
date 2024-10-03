package com.kevin.prayerappservice.exceptions;

import org.springframework.http.HttpStatus;

public class DataValidationException extends IllegalArgumentException {
    private final String[] dataValidationErrors;
    private final HttpStatus httpStatus;

    public DataValidationException(String[] dataValidationErrors){
        super();
        httpStatus = HttpStatus.BAD_REQUEST;
        this.dataValidationErrors = dataValidationErrors;
    }

    public DataValidationException(HttpStatus httpStatus, String[] dataValidationErrors){
        super();
        this.httpStatus = httpStatus;
        this.dataValidationErrors = dataValidationErrors;
    }

    public String[] getDataValidationErrors() {
        return dataValidationErrors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

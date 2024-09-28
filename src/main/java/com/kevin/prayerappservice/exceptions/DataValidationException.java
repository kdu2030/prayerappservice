package com.kevin.prayerappservice.exceptions;

import lombok.Getter;

@Getter
public class DataValidationException extends RuntimeException {
    private String[] dataValidationErrors;

    public DataValidationException(String[] dataValidationErrors){
        super();
        this.dataValidationErrors = dataValidationErrors;
    }

}

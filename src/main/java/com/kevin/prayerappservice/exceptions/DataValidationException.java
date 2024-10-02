package com.kevin.prayerappservice.exceptions;

public class DataValidationException extends RuntimeException {
    private final String[] dataValidationErrors;

    public DataValidationException(String[] dataValidationErrors){
        super();
        this.dataValidationErrors = dataValidationErrors;
    }

    public String[] getDataValidationErrors() {
        return dataValidationErrors;
    }


}

package com.kevin.prayerappservice.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DataValidationError extends Error {
    private String[] dataValidationErrors;


    @Builder(builderMethodName = "dataValidationErrorBuilder")
    DataValidationError(String errorCode, String message, String url, String reqMethod, String[] dataValidationErrors) {
        super(errorCode, message, url, reqMethod);
        this.dataValidationErrors = dataValidationErrors;
    }
}

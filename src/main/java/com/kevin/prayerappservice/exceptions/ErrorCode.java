package com.kevin.prayerappservice.exceptions;

import lombok.Getter;


@Getter
public enum ErrorCode {
    GENERIC_ERROR("GEN_ERROR", "An internal server error has occurred."),
    REQUEST_DATA_NULL_ERROR("REQUEST_DATA_NULL_ERROR", "A required field in the request object cannot be null");


    private final String errCode;
    private final String errMessageKey;

    ErrorCode(String errCode, String errMessageKey){
        this.errCode = errCode;
        this.errMessageKey = errMessageKey;
    }

}

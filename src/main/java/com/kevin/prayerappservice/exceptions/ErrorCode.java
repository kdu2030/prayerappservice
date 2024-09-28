package com.kevin.prayerappservice.exceptions;

import lombok.Getter;


@Getter
public enum ErrorCode {
    GENERIC_ERROR("GEN_ERROR", "An internal server error has occurred."),
    DATA_VALIDATION_ERROR("DATA_VALIDATION_ERROR", "A data validation error occurred.");



    private final String errCode;
    private final String errMessageKey;

    ErrorCode(String errCode, String errMessageKey){
        this.errCode = errCode;
        this.errMessageKey = errMessageKey;
    }

}

package com.kevin.prayerappservice.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String errorCode;
    private String message;
    private String url;
    private String reqMethod;
}

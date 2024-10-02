package com.kevin.prayerappservice.exceptions;

public class Error {
    private String errorCode;
    private String message;
    private String url;
    private String reqMethod;

    public Error(ErrorBuilder builder) {
        errorCode = builder.errorCode;
        message = builder.message;
        url = builder.url;
        reqMethod = builder.reqMethod;
    }

    public Error(String errorCode, String message, String url, String reqMethod) {
        this.errorCode = errorCode;
        this.message = message;
        this.url = url;
        this.reqMethod = reqMethod;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public static ErrorBuilder builder(){
        return new ErrorBuilder();
    }

    public static class ErrorBuilder {
        private String errorCode;
        private String message;
        private String url;
        private String reqMethod;

        public ErrorBuilder errorCode(String errorCode){
            this.errorCode = errorCode;
            return this;
        }

        public ErrorBuilder message(String message){
            this.message = message;
            return this;
        }

        public ErrorBuilder url(String url){
            this.url = url;
            return this;
        }

        public ErrorBuilder reqMethod(String reqMethod){
            this.reqMethod = reqMethod;
            return this;
        }

        public Error build(){
            return new Error(this);
        }
    }
}

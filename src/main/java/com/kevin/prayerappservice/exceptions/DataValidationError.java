package com.kevin.prayerappservice.exceptions;


public class DataValidationError extends Error {
    private String[] dataValidationErrors;

    public DataValidationError(DataValidationErrorBuilder builder){
        super(builder.errorCode, builder.message, builder.url, builder.reqMethod);
        dataValidationErrors = builder.dataValidationErrors;
    }

    public String[] getDataValidationErrors() {
        return dataValidationErrors;
    }

    public void setDataValidationErrors(String[] dataValidationErrors) {
        this.dataValidationErrors = dataValidationErrors;
    }

    public static DataValidationErrorBuilder dataValidationErrorBuilder(){
        return new DataValidationErrorBuilder();
    }

    public static class DataValidationErrorBuilder {
        private String[] dataValidationErrors;
        private String errorCode;
        private String message;
        private String url;
        private String reqMethod;

        public DataValidationErrorBuilder dataValidationErrors(String[] dataValidationErrors){
            this.dataValidationErrors = dataValidationErrors;
            return this;
        }

        public DataValidationErrorBuilder errorCode(String errorCode){
            this.errorCode = errorCode;
            return this;
        }

        public DataValidationErrorBuilder message(String message){
            this.message = message;
            return this;
        }

        public DataValidationErrorBuilder url(String url){
            this.url = url;
            return this;
        }

        public DataValidationErrorBuilder reqMethod(String reqMethod){
            this.reqMethod = reqMethod;
            return this;
        }

        public DataValidationError build(){
            return new DataValidationError(this);
        }



    }
}

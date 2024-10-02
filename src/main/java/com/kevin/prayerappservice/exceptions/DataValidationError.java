package com.kevin.prayerappservice.exceptions;


public class DataValidationError extends Error {
    private String[] dataValidationErrors;

    public DataValidationError(DataValidationErrorBuilder builder){
        super(builder);
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

    public static class DataValidationErrorBuilder extends Error.ErrorBuilder {
        private String[] dataValidationErrors;

        public DataValidationErrorBuilder dataValidationErrors(String[] dataValidationErrors){
            this.dataValidationErrors = dataValidationErrors;
            return this;
        }

    }
}

package com.kevin.prayerappservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;

@ControllerAdvice
public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(HttpServletRequest request, Exception exception){
        logger.error("Generic Error occurred", exception);

        Error error = Error.builder()
                .errorCode(ErrorCode.GENERIC_ERROR.getErrCode())
                .message(ErrorCode.GENERIC_ERROR.getErrMessageKey())
                .url(request.getRequestURL().toString())
                .reqMethod(request.getMethod())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Error> handleValidationException(HttpServletRequest request, DataIntegrityViolationException exception){
        logger.error("Data validation error occurred.", exception);

        String fullExceptionMessage = exception.getMostSpecificCause().getLocalizedMessage();
        String[] exceptionMessageParts = fullExceptionMessage.split("Detail: ");

        String constraintName = exceptionMessageParts[exceptionMessageParts.length - 1].trim();

        Error error = DataValidationError.dataValidationErrorBuilder()
                .dataValidationErrors(new String[] { constraintName })
                .errorCode(ErrorCode.DATA_VALIDATION_ERROR.getErrCode())
                .message(ErrorCode.DATA_VALIDATION_ERROR.getErrMessageKey())
                .reqMethod(request.getMethod())
                .url(request.getRequestURL().toString())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException exception){
        logger.error("Data validation error occurred", exception);
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        String[] errorMessages = new String[constraintViolations.size()];

        int i = 0;
        for(ConstraintViolation<?> constraintViolation: constraintViolations){
            errorMessages[i++] = String.format("%s %s", constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }

        Error error = DataValidationError.dataValidationErrorBuilder()
                .dataValidationErrors(errorMessages)
                .errorCode(ErrorCode.DATA_VALIDATION_ERROR.getErrCode())
                .message(ErrorCode.DATA_VALIDATION_ERROR.getErrMessageKey())
                .reqMethod(request.getMethod())
                .url(request.getRequestURL().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Error> handleTransactionSystemException(HttpServletRequest request, TransactionSystemException exception){
        if(exception.getMostSpecificCause() instanceof ConstraintViolationException){
            return handleConstraintViolationException(request, (ConstraintViolationException) exception.getMostSpecificCause());
        }
        return handleException(request, exception);
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<Error> handleDataValidationException(HttpServletRequest request, DataValidationException exception){
        logger.error("Data validation error occurred.", exception);

        Error error = DataValidationError.dataValidationErrorBuilder()
                .dataValidationErrors(exception.getDataValidationErrors())
                .errorCode(ErrorCode.DATA_VALIDATION_ERROR.getErrCode())
                .message(ErrorCode.DATA_VALIDATION_ERROR.getErrMessageKey())
                .reqMethod(request.getMethod())
                .url(request.getRequestURL().toString())
                .build();
        return new ResponseEntity<>(error, exception.getHttpStatus());
    }


}

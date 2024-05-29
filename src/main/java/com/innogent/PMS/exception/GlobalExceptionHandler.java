package com.innogent.PMS.exception;

import com.innogent.PMS.exception.customException.NoSuchGoalExistsException;
import com.innogent.PMS.exception.customException.NoSuchUserExistsException;
import com.innogent.PMS.exception.customException.UserAlreadyExistsException;
import com.innogent.PMS.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    //User Not Found Exceptions
    @ExceptionHandler(value = NoSuchUserExistsException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchUserExistsException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }
    //User Already Exists Exceptions
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSupplierAlreadyExistsException(UserAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }
    //Goal related Exceptions
    @ExceptionHandler(value = NoSuchGoalExistsException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchGoalExistsException(NoSuchGoalExistsException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }
    //Invalid Http method Exception
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public @ResponseBody ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
    }
}

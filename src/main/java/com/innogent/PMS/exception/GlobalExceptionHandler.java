package com.innogent.PMS.exception;

import com.innogent.PMS.exception.customException.NoSuchSupplierExistsException;
import com.innogent.PMS.exception.customException.SupplierAlreadyExistsException;
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

    @ExceptionHandler(value = NoSuchSupplierExistsException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchSupplierExistsException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }
    @ExceptionHandler(value = SupplierAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSupplierAlreadyExistsException(SupplierAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public @ResponseBody ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
    }
}

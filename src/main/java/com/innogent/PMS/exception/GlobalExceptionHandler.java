package com.innogent.PMS.exception;

import com.innogent.PMS.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GenericException.class)
    protected ResponseEntity<ErrorResponse> handleException(GenericException exception, WebRequest request, HttpServletRequest response) {
        HttpStatus httpStatus = exception.getHttpStatus();
        Integer errorCode = httpStatus.value();
        String errorMessage = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage, httpStatus.name());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception exception, WebRequest request, HttpServletRequest response) throws GenericException {
        return handleException(new GenericException(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR),request,response);
    }
//    //User Not Found Exceptions
//    @ExceptionHandler(value = NoSuchUserExistsException.class)
//    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchUserExistsException ex){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
//    }
//    //User Already Exists Exceptions
//    @ExceptionHandler(value = UserAlreadyExistsException.class)
//    public ResponseEntity<ErrorResponse> handleSupplierAlreadyExistsException(UserAlreadyExistsException ex){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
//    }
//    //Goal related Exceptions
//    @ExceptionHandler(value = NoSuchGoalExistsException.class)
//    public ResponseEntity<ErrorResponse> handleNoSuchGoalExistsException(NoSuchGoalExistsException ex){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
//    }
//    //Invalid Http method Exception
//    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    public @ResponseBody ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
//        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
//    }
    // tanish code
@ExceptionHandler(EntityNotFoundException.class)
public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
}

//    @ExceptionHandler(MapperException.class)
//    public ResponseEntity<String> handleMapperException(MapperException ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception ex) {
//        // You can log the exception here if needed
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//    }
}


package com.innogent.PMS.exception.customException;

import com.innogent.PMS.exception.GenericException;
import org.springframework.http.HttpStatus;

public class NoSuchGoalExistsException extends GenericException {
    public NoSuchGoalExistsException(String message, HttpStatus httpStatus){
        super (message,httpStatus);
    }
}

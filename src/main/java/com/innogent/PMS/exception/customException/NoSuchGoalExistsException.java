package com.innogent.PMS.exception.customException;

public class NoSuchGoalExistsException extends RuntimeException{
    public NoSuchGoalExistsException(){
    }
    public NoSuchGoalExistsException(String message){ super(message);}
}

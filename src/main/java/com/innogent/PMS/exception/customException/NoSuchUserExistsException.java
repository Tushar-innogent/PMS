package com.innogent.PMS.exception.customException;

public class NoSuchUserExistsException extends RuntimeException{
    public NoSuchUserExistsException(){
    }
    public NoSuchUserExistsException(String message){ super(message);}
}

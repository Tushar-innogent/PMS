package com.innogent.PMS.exception.customException;

public class NoSuchSupplierExistsException extends RuntimeException{
    public NoSuchSupplierExistsException(){
    }
    public NoSuchSupplierExistsException(String message){ super(message);}
}

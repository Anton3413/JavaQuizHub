package com.example.javaquizhub.exception.custom_exceptions;

public class AccountNotActivatedException  extends RuntimeException{

    public AccountNotActivatedException(String message){
        super(message);
    }
}

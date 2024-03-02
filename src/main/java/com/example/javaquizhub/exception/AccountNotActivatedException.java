package com.example.javaquizhub.exception;

public class AccountNotActivatedException  extends RuntimeException{


    public AccountNotActivatedException(String message){
        super(message);
    }
}

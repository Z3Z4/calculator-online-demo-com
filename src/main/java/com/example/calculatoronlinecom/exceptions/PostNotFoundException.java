package com.example.calculatoronlinecom.exceptions;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String msg) {
        super(msg);
    }
}

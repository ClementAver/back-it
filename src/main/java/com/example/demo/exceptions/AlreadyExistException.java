package com.example.demo.exceptions;

public class AlreadyExistException extends Exception{
    public AlreadyExistException (String message) {
        super(message);
    }
}
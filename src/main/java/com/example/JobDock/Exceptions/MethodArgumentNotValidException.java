package com.example.JobDock.Exceptions;

public class MethodArgumentNotValidException extends Exception{
    public MethodArgumentNotValidException() {
        super("Please enter valid data");
    }
}

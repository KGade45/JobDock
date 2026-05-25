package com.example.JobDock.Exceptions;

public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException() {
        super("Application Not found");
    }
}

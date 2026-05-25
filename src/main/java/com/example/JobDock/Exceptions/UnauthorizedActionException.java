package com.example.JobDock.Exceptions;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException() {
        super("You are unauthorized");
    }
}

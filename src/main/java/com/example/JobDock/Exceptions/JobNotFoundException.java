package com.example.JobDock.Exceptions;

public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException() {
        super("Job with mentioned Id is not found");
    }
}

package com.example.JobDock.Exceptions;

public class AlreadyAppliedToJobException extends RuntimeException {
    public AlreadyAppliedToJobException() {
        super("You have already applied to this job");
    }
}

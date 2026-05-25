package com.example.JobDock.Service;

import com.example.JobDock.Controller.Application.ApplicationController;
import com.example.JobDock.Exceptions.AlreadyAppliedToJobException;
import com.example.JobDock.Exceptions.UnauthorizedActionException;
import com.example.JobDock.Model.Application.Application;
import com.example.JobDock.Model.Application.ApplicationStatus;
import com.example.JobDock.Model.Job.Job;
import com.example.JobDock.Model.User;
import com.example.JobDock.Repository.ApplicationRepository;
import com.example.JobDock.Repository.JobRepository;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ApplicationService {
    private final ApplicationRepository appRepo;
    private final JobRepository jobRepo;

    ApplicationService(ApplicationRepository appRepo, JobRepository jobRepo) {
        this.appRepo = appRepo;
        this.jobRepo = jobRepo;
    }

    public Application apply(User user, Long jobId, String resumeUrl) {

        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("No job with mentioned Id"));

        if(appRepo.existsByApplicantAndJob(user, job)) {
            throw new AlreadyAppliedToJobException();
        }
        Application application = new Application();
        application.setApplicant(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setResumeUrl(resumeUrl);
        application.setUpdatedAt(application.getAppliedAt());
        appRepo.save(application);
        return application;
    }

    public List<Application> myApplications(User user) {
        return appRepo.findAllByApplicant(user);
    }

    public Application updateStatus(User user, long id, ApplicationStatus status) {
        Application application = appRepo.findById(id)
                .orElseThrow();
        if(!Objects.equals(application.getJob().getPostedBy().getId(), user.getId())) {
            throw new UnauthorizedActionException();
        }

        application.setStatus(status);
        application.setUpdatedAt(LocalDateTime.now());
        appRepo.save(application);
        return application;
    }
}

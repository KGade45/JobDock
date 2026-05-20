package com.example.JobDock.Service;

import com.example.JobDock.Exceptions.AlreadyAppliedToJobException;
import com.example.JobDock.Model.Application.Application;
import com.example.JobDock.Model.Application.ApplicationStatus;
import com.example.JobDock.Model.Job.Job;
import com.example.JobDock.Model.User;
import com.example.JobDock.Repository.ApplicationRepository;
import com.example.JobDock.Repository.JobRepository;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}

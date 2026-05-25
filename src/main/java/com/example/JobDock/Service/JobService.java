package com.example.JobDock.Service;

import com.example.JobDock.Exceptions.JobNotFoundException;
import com.example.JobDock.Exceptions.UnauthorizedActionException;
import com.example.JobDock.Model.Application.Application;
import com.example.JobDock.Model.Job.Job;
import com.example.JobDock.Model.User;
import com.example.JobDock.Repository.ApplicationRepository;
import com.example.JobDock.Repository.JobRepository;
import com.example.JobDock.dto.Job.JobRequest;
import com.example.JobDock.dto.Job.JobResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JobService {

    private JobRepository jobRepo;
    private ApplicationRepository appRepo;

    JobService(JobRepository jobRepo, ApplicationRepository appRepo) {
        this.jobRepo = jobRepo;
        this.appRepo = appRepo;
    }

    public List<JobResponse> getAllJobs() {
        List<Job> jobs = jobRepo.findAll();

        return jobs.stream()
                .map(job -> new JobResponse(
                        job.getTitle(),
                        job.isActive(),
                        job.getJobType(),
                        job.getSalary(),
                        job.getCompany(),
                        job.getLocation(),
                        job.getPostedOn()
                ))
                .collect(Collectors.toList());
    }

    public JobResponse postJob(JobRequest request, User user) {
        Job newJob = new Job();
        newJob.setJobType(request.getJobType());
        newJob.setCompany(request.getCompany());
        newJob.setActive(request.isActive());
        newJob.setDescription(request.getDescription());
        newJob.setLocation(request.getLocation());
        newJob.setSalary(request.getSalary());
        newJob.setTitle(request.getTitle());
        newJob.setPostedBy(user);
        newJob.setRequiredExp(request.getRequiredExp());


        Job savedJob = jobRepo.save(newJob);
        JobResponse response = new JobResponse();
        response.setActive(savedJob.isActive());
        response.setSalary(savedJob.getSalary());
        response.setTitle(savedJob.getTitle());
        response.setJobType(savedJob.getJobType());
        response.setPostedOn(savedJob.getPostedOn());
        response.setLocation(savedJob.getLocation());
        response.setCompany(savedJob.getCompany());


        return response;
    }

    public List<Application> getAllApplications(long jobId, User user) {
        Job job = jobRepo.findById(jobId)
                .orElseThrow();

        if(!Objects.equals(job.getPostedBy().getId(), user.getId())) {
            throw new UnauthorizedActionException();
        }
        return appRepo.findAllByJob(job);
    }
}

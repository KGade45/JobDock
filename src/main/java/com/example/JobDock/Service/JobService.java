package com.example.JobDock.Service;

import com.example.JobDock.Model.Job.Job;
import com.example.JobDock.Model.Job.JobType;
import com.example.JobDock.Model.User;
import com.example.JobDock.Repository.JobRepository;
import com.example.JobDock.dto.Job.JobRequest;
import com.example.JobDock.dto.Job.JobResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private JobRepository jobRepo;

    JobService(JobRepository jobRepo) {
        this.jobRepo = jobRepo;
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
}

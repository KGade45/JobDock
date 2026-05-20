package com.example.JobDock.Controller.Job;

import com.example.JobDock.Model.User;
import com.example.JobDock.Service.JobService;
import com.example.JobDock.Service.UserService;
import com.example.JobDock.dto.Job.JobRequest;
import com.example.JobDock.dto.Job.JobResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final UserService userService;
    private final JobService jobService;

    JobController(UserService userService, JobService jobService) {
        this.userService = userService;
        this.jobService = jobService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<JobResponse> postJob(@Valid @RequestBody JobRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUserByEmail(email);
        JobResponse response = jobService.postJob(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }
}

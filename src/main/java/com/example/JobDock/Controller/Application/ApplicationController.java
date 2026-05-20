package com.example.JobDock.Controller.Application;

import com.example.JobDock.Model.Application.Application;
import com.example.JobDock.Model.Job.Job;
import com.example.JobDock.Model.User;
import com.example.JobDock.Repository.JobRepository;
import com.example.JobDock.Service.ApplicationService;
import com.example.JobDock.Service.JobService;
import com.example.JobDock.Service.UserService;
import com.example.JobDock.dto.Application.ApplicationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/application")
public class ApplicationController {

    private final UserService userService;
    private final ApplicationService applicationService;

    ApplicationController(UserService userService, ApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('SEEKER')")
    public ResponseEntity<Application> apply(@RequestBody ApplicationRequest request) {
        System.out.println("control in Controller" + request);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUserByEmail(email);
        System.out.println("fetched user" + user);
        Application application = applicationService.apply(user, request.getJobId(), request.getResumeUrl());
        return ResponseEntity.ok(application);
    }
}

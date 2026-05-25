package com.example.JobDock.Controller.Application;

import com.example.JobDock.Model.Application.Application;
import com.example.JobDock.Model.Application.ApplicationStatus;
import com.example.JobDock.Model.User;
import com.example.JobDock.Service.ApplicationService;
import com.example.JobDock.Service.UserService;
import com.example.JobDock.dto.Application.ApplicationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        User user = this.getUser();
        Application application = applicationService.apply(user, request.getJobId(), request.getResumeUrl());
        return ResponseEntity.ok(application);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('SEEKER')")
    public ResponseEntity<List<Application>> myApplications() {
        User user = this.getUser();
        return ResponseEntity.ok(applicationService.myApplications(user));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable long id, @RequestBody ApplicationStatus status){
        User user = this.getUser();
        return ResponseEntity.ok(applicationService.updateStatus(user, id, status));
    }

    // helper methods
    private User getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.loadUserByEmail(email);
    }
}

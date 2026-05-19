package com.example.JobDock.dto.Job;

import com.example.JobDock.Model.Job.JobType;
import com.example.JobDock.Model.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobRequest {

    @NotBlank(message = "Job title cannot be blank")
    private String title;

    @NotBlank(message = "experience cannot be blank")
    private String requiredExp;

    @NotBlank(message = "Description is required")
    private String description;

    private boolean isActive;

    @NotNull
    private JobType jobType;

    private String salary;

    @NotBlank(message = "company name cannot be empty")
    private String company;

    @NotBlank(message = "Location must be specified")
    private String location;

}

package com.example.JobDock.dto.Job;

import com.example.JobDock.Model.Job.JobType;
import com.example.JobDock.Model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    private String title;
    private boolean isActive;
    private JobType jobType;
    private String salary;
    private String company;
    private String Location;
    private LocalDateTime postedOn;
}

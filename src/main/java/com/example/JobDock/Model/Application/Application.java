package com.example.JobDock.Model.Application;

import com.example.JobDock.Model.Job.Job;
import com.example.JobDock.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User applicant;

    @ManyToOne
    private Job job;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String resumeUrl;

    private LocalDateTime appliedAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
}

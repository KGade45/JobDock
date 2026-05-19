package com.example.JobDock.Model.Job;

import com.example.JobDock.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobs")
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String requiredExp;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    LocalDateTime postedOn = LocalDateTime.now();

    @Column(nullable = false)
    boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    JobType jobType;

    @ManyToOne
    @JoinColumn(name = "posted_by", nullable = false)
    User postedBy;

    String salary;

    @Column(nullable = false)
    String company;

    String location;

}

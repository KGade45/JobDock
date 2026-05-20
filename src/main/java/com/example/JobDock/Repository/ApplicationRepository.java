package com.example.JobDock.Repository;

import com.example.JobDock.Model.Application.Application;
import com.example.JobDock.Model.Job.Job;
import com.example.JobDock.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByApplicantAndJob(User user, Job job);
}

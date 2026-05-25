package com.example.JobDock.Repository;

import com.example.JobDock.Model.Application.Application;
import com.example.JobDock.Model.Job.Job;
import com.example.JobDock.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByApplicantAndJob(User user, Job job);
    List<Application> findAllByApplicant(User user);
    List<Application> findAllByJob(Job job);
}

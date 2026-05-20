package com.example.JobDock.dto.Application;

import lombok.Data;

@Data
public class ApplicationRequest {
    private Long jobId;
    private String resumeUrl;
}

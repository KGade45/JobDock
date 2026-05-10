package com.example.JobDock.dto;

import com.example.JobDock.Model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
}

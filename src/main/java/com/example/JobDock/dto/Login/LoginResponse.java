package com.example.JobDock.dto.Login;

import com.example.JobDock.dto.UserResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginResponse {

    String jwtToken;
    UserResponse user;
}

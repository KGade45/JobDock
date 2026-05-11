package com.example.JobDock.Service;

import com.example.JobDock.Exceptions.EmailAlreadyExistsException;
import com.example.JobDock.Model.User;
import com.example.JobDock.Repository.UserRepository;
import com.example.JobDock.config.SecurityConfig;
import com.example.JobDock.dto.Login.LoginRequest;
import com.example.JobDock.dto.Login.LoginResponse;
import com.example.JobDock.dto.RegisterRequest;
import com.example.JobDock.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        User savedUser = userRepo.save(user);
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setName(savedUser.getName());
        response.setRole(savedUser.getRole());
        return response;
    }

    public User loadUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public LoginResponse login(LoginRequest userData){
        User user = userRepo.findByEmail(userData.getEmail())
                .orElseThrow(() -> new RuntimeException("Email does not exists"));

        if (!passwordEncoder.matches(userData.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setRole(user.getRole());
        userResponse.setId(user.getId());


        LoginResponse response = new LoginResponse();
        response.setUser(userResponse);
        response.setJwtToken(token);

        return response;
    }

}


package com.example.JobDock.Controller;

import com.example.JobDock.Exceptions.EmailAlreadyExistsException;
import com.example.JobDock.Model.User;
import com.example.JobDock.Service.JwtService;
import com.example.JobDock.Service.UserService;
import com.example.JobDock.dto.RegisterRequest;
import com.example.JobDock.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping
    public void greet(){
        System.out.println("Hello there");
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse user = userService.register(request);

        URI location = URI.create("/users/" + user.getId());

        return ResponseEntity.created(location).body(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserResponse userResponse) {
        String token = jwtService.generateToken(userResponse.getEmail(), userResponse.getRole());
        System.out.println(token);
        return token;
    }
}


package com.example.userauthservice.userauth;

import com.example.userauthservice.userauth.dto.LoginRequest;
import com.example.userauthservice.userauth.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.loginUser(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        String token = userService.registerUser(registerRequest);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}

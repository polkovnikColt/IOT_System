package com.example.userauthservice.userauth;

import com.example.userauthservice.userauth.dto.LoginRequest;
import com.example.userauthservice.userauth.dto.RegisterRequest;
import com.example.userauthservice.userauth.kafka.RegistrationProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RegistrationProducer registrationProducer;


    public UserService(UserRepository userRepository, RegistrationProducer registrationProducer) {
        this.userRepository = userRepository;
        this.registrationProducer = registrationProducer;
    }

    public String loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginRequest.getEmail()));

        if (loginRequest.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        return jwtToken;
    }

    public String registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setCreatedDate(Instant.now());

        userRepository.save(user);

        registrationProducer.sendMessage(user.getId().toString());

        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        return jwtToken;
    }
}

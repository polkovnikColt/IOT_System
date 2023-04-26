package com.example.userauthservice.userauth.dto;

import java.util.UUID;

public class AuthResponse {

    private UUID id;
    private String jwtToken;

    public AuthResponse() {
    }

    public AuthResponse(UUID id, String jwtToken) {
        this.id = id;
        this.jwtToken = jwtToken;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}

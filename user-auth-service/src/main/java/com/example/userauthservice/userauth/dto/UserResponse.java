package com.example.userauthservice.userauth.dto;

import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String jwtToken;

    public UserResponse() {
    }

    public UserResponse(UUID id, String jwtToken) {
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

package com.example.deviceregistrationservice.registration;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "device_registration")
public class DeviceRegistration {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "device_id")
    private UUID deviceId;

    @Column(name = "createdDate")
    private Instant createdDate;

    public DeviceRegistration() {

    }

    public DeviceRegistration(UUID id, Status status, UUID deviceId) {
        this.id = id;
        this.status = status;
        this.deviceId = deviceId;
        this.createdDate = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getName() {
        return deviceId;
    }

    public void setName(UUID name) {
        this.deviceId = name;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    // Getters and setters, and other methods if needed
}
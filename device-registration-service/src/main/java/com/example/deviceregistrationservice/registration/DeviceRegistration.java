package com.example.deviceregistrationservice.registration;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "device_registration")
public class DeviceRegistration {

    @Id
    @Column(name = "device_id")
    private UUID deviceId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "createdDate")
    private Instant createdDate;

    public DeviceRegistration() {

    }

    public DeviceRegistration(UUID deviceId, Status status) {
        this.deviceId = deviceId;
        this.status = status;
        this.createdDate = Instant.now();
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

}
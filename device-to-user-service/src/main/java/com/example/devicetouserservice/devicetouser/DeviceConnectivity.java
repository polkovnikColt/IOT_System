package com.example.devicetouserservice.devicetouser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "device_connectivity")
public class DeviceConnectivity {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "devices")
    private String devices;

    @Column(name = "created_date")
    private Instant createdDate;

    public DeviceConnectivity() {
    }

    public DeviceConnectivity(UUID userId, String devices) {
        this.devices = devices;
        this.userId = userId;
        this.createdDate = Instant.now();
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}

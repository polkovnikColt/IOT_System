package com.example.devicetouserservice.devicetouser;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "device_connectivity")
public class DeviceConnectivity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "devices", columnDefinition = "json")
    @Convert(converter = JsonStringListConverter.class)
    private List<String> devices = new ArrayList<>();

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_date")
    private Instant createdDate;

    public DeviceConnectivity() {
    }

    public DeviceConnectivity(UUID id, List<String> devices, UUID userId) {
        this.id = id;
        this.devices = devices;
        this.userId = userId;
        this.createdDate = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
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

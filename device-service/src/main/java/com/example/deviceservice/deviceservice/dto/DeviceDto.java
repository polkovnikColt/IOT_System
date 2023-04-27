package com.example.deviceservice.deviceservice.dto;

import java.util.UUID;

public class DeviceDto {
    private UUID deviceId;
    private String name;

    public DeviceDto() {
    }

    public DeviceDto(UUID deviceId, String name) {
        this.deviceId = deviceId;
        this.name = name;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

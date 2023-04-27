package com.example.device.deviceservice.dto;

import java.util.UUID;

public class DeviceDto {
    private UUID deviceID;
    private String name;

    public DeviceDto() {
    }

    public DeviceDto(UUID deviceId, String name) {
        this.deviceID = deviceId;
        this.name = name;
    }

    public UUID getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(UUID deviceID) {
        this.deviceID = deviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

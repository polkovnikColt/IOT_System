package com.example.devicetouserservice.devicetouser.dto;

import java.util.UUID;

public class DeviceRequest {
    private UUID deviceID;

    private String status;

    public DeviceRequest() {
    }

    public DeviceRequest(UUID deviceID, String status) {
        this.deviceID = deviceID;
        this.status = status;
    }

    public UUID getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(UUID deviceID) {
        this.deviceID = deviceID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

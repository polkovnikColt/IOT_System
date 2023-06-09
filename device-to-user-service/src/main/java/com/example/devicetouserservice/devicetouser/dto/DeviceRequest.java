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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceRequest{");
        sb.append("deviceID=").append(deviceID);
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.example.device.deviceservice.dto;

import java.util.UUID;

public class ChangeStatusRequest {
    private UUID deviceID;
    private String status;

    public ChangeStatusRequest() {
    }

    public ChangeStatusRequest(UUID deviceID, String status) {
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
        final StringBuilder sb = new StringBuilder("ChangeStatusRequest{");
        sb.append("deviceID=").append(deviceID);
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

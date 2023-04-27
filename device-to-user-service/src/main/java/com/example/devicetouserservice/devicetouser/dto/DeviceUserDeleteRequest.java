package com.example.devicetouserservice.devicetouser.dto;

import java.util.UUID;

public class DeviceUserDeleteRequest {
    private UUID userID;
    private UUID deviceID;

    public DeviceUserDeleteRequest() {
    }

    public DeviceUserDeleteRequest(UUID userID, UUID deviceID) {
        this.userID = userID;
        this.deviceID = deviceID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public UUID getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(UUID deviceID) {
        this.deviceID = deviceID;
    }
}

package com.example.deviceregistrationservice.registration.request;

import java.util.UUID;

public class DeviceRegistrationRequest {
    private UUID deviceID;

    public DeviceRegistrationRequest() {
    }

    public DeviceRegistrationRequest(UUID deviceID) {
        this.deviceID = deviceID;
    }

    public UUID getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(UUID deviceID) {
        this.deviceID = deviceID;
    }
}

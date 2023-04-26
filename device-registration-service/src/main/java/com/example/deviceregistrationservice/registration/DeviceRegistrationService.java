package com.example.deviceregistrationservice.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceRegistrationService {

    @Autowired
    private DeviceRegistrationRepository deviceRegistrationRepository;

    public Optional<DeviceRegistration> getDeviceRegistrationById(UUID id) {
        return deviceRegistrationRepository.findById(id);
    }

    public void deleteDeviceRegistrationById(UUID id) {
        deviceRegistrationRepository.deleteById(id);
    }

}
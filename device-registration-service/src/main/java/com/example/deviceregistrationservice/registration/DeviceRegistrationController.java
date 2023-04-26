package com.example.deviceregistrationservice.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deviceRegistr")
public class DeviceRegistrationController {

    private final DeviceRegistrationService deviceRegistrationService;

    public DeviceRegistrationController(DeviceRegistrationService deviceRegistrationService) {
        this.deviceRegistrationService = deviceRegistrationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceRegistration> getDeviceRegistrationById(@PathVariable("id") UUID id) {
        Optional<DeviceRegistration> optionalDeviceRegistration = deviceRegistrationService.getDeviceRegistrationById(id);
        return optionalDeviceRegistration.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDeviceRegistrationById(@PathVariable("id") UUID id) {
        Optional<DeviceRegistration> optionalDeviceRegistration = deviceRegistrationService.getDeviceRegistrationById(id);
        if (optionalDeviceRegistration.isPresent()) {
            deviceRegistrationService.deleteDeviceRegistrationById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
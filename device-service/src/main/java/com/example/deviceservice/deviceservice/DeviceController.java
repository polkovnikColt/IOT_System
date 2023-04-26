package com.example.deviceservice.deviceservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PatchMapping("/changeStatus")
    public ResponseEntity<Device> changeDeviceStatus(@RequestBody Device device) {
        Optional<Device> updatedDevice = deviceService.changeDeviceStatus(device.getId(), device.getStatus());
        return updatedDevice.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Device> getDeviceStatus(@PathVariable UUID id) {
        Optional<Device> device = deviceService.getDeviceById(id);
        return device.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
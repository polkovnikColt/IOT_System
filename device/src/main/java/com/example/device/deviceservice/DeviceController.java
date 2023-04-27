package com.example.device.deviceservice;

import com.example.device.deviceservice.dto.DeviceDto;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/create")
    public ResponseEntity<Status> createDevice(@RequestBody DeviceDto deviceRequest) {
        Status status = deviceService.createDevice(deviceRequest);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    //Internal call
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDeviceRegistrationById(@PathVariable("id") UUID id) {
        boolean isDeleted = deviceService.deleteDevice(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
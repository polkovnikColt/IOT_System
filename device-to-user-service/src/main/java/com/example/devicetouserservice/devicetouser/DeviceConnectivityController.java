package com.example.devicetouserservice.devicetouser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deviceToUser")
public class DeviceConnectivityController {

    private final DeviceConnectivityService deviceConnectivityService;

    public DeviceConnectivityController(DeviceConnectivityService deviceConnectivityService) {
        this.deviceConnectivityService = deviceConnectivityService;
    }

    @GetMapping("/getDevice/{id}")
    public ResponseEntity<DeviceConnectivity> getDeviceConnectivityById(@PathVariable UUID id) {
        DeviceConnectivity deviceConnectivity = deviceConnectivityService.getDeviceConnectivityById(id);
        return new ResponseEntity<>(deviceConnectivity, HttpStatus.OK);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<DeviceConnectivity>> getDeviceConnectivitiesByUserId(@PathVariable UUID userId) {
        List<DeviceConnectivity> deviceConnectivities = deviceConnectivityService.getDeviceConnectivitiesByUserId(userId);
        return new ResponseEntity<>(deviceConnectivities, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UUID> deleteDeviceConnectivityById(@PathVariable UUID id) {
        deviceConnectivityService.deleteDeviceConnectivityById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/connect/{id}")
    public ResponseEntity<UUID> connectDevice(@PathVariable UUID id) {
        UUID deviceId = deviceConnectivityService.connectDevice(id);
        return new ResponseEntity<>(deviceId, HttpStatus.OK);
    }

    @PostMapping("/disconnect/{id}")
    public ResponseEntity<UUID> disconnectDevice(@PathVariable UUID id) {
        UUID deviceId = deviceConnectivityService.disconnectDevice(id);
        return new ResponseEntity<>(deviceId, HttpStatus.OK);
    }
}

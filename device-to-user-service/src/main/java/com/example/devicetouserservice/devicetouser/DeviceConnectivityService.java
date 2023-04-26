package com.example.devicetouserservice.devicetouser;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceConnectivityService {

    private final DeviceConnectivityRepository deviceConnectivityRepository;

    public DeviceConnectivityService(DeviceConnectivityRepository deviceConnectivityRepository) {
        this.deviceConnectivityRepository = deviceConnectivityRepository;
    }

    public DeviceConnectivity getDeviceConnectivityById(UUID id) {
        return deviceConnectivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DeviceConnectivity not found with id: " + id));
    }

    public List<DeviceConnectivity> getDeviceConnectivitiesByUserId(UUID userId) {
        return deviceConnectivityRepository.findAll().stream()
                .filter(deviceConnectivity -> deviceConnectivity.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void deleteDeviceConnectivityById(UUID id) {
        deviceConnectivityRepository.deleteById(id);
    }

    public UUID connectDevice(UUID id) {
        DeviceConnectivity deviceConnectivity = getDeviceConnectivityById(id);
        // Implement your specific logic for connecting the device here
        // For example, update the deviceConnectivity object, save the changes, and return the connected device ID

        return deviceConnectivity.getId();
    }

    public UUID disconnectDevice(UUID id) {
        DeviceConnectivity deviceConnectivity = getDeviceConnectivityById(id);
        // Implement your specific logic for disconnecting the device here
        // For example, update the deviceConnectivity object, save the changes, and return the disconnected device ID

        return deviceConnectivity.getId();
    }
}
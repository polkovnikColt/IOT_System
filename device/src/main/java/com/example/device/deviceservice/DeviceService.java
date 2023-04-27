package com.example.device.deviceservice;

import com.example.device.deviceservice.dto.DeviceDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    public Status createDevice(DeviceDto deviceDto) {
        deviceRepository.save(new Device(deviceDto.getDeviceID(), Status.OFFLINE, deviceDto.getName()));
        return Status.OFFLINE;
    }

    public Optional<Device> changeDeviceStatus(UUID id, Status status) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            device.setStatus(status);
            deviceRepository.save(device);
        }
        return deviceOptional;
    }

    public boolean deleteDevice(UUID deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        if (device.isEmpty()) {
            return false;
        }
        RestTemplate restTemplate = new RestTemplate();

        String registrationUrl = "http://localhost:9002/api/v1/deviceRegistr/delete/" + deviceId;
        HttpEntity<String> requestEntity = new HttpEntity<>(null);

        ResponseEntity<String> response = restTemplate.exchange(registrationUrl, HttpMethod.DELETE, requestEntity, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Cannot delete device registration for deviceID: " + deviceId);
        }
        deviceRepository.deleteById(deviceId);
        return true;
    }


    public Optional<Device> getDeviceById(UUID id) {
        return deviceRepository.findById(id);
    }
}
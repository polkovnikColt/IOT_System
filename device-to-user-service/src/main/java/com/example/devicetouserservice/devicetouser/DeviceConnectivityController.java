package com.example.devicetouserservice.devicetouser;

import com.example.devicetouserservice.devicetouser.dto.DeviceUserDeleteRequest;
import com.example.devicetouserservice.devicetouser.dto.DeviceUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
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

    @GetMapping("/getDeviceById")
    public ResponseEntity<String> getDeviceById(@RequestParam("userId") String userId,
                                                @RequestParam("deviceId") String deviceId) throws JsonProcessingException {
        DeviceConnectivity deviceConnectivity = deviceConnectivityService.getDeviceConnectivityById(UUID.fromString(userId));
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> devices = objectMapper.readValue(deviceConnectivity.getDevices(), Set.class);
        if (devices.contains(deviceId)) {
            // Return the device object in the response body
            return new ResponseEntity<>(deviceId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deviceId, HttpStatus.NOT_FOUND);
        }

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

    @PostMapping("/delete")
    public ResponseEntity<String> deleteDeviceToUser(@RequestBody DeviceUserDeleteRequest request) throws JsonProcessingException {
        if (deviceConnectivityService.deleteDeviceForUser(request.getUserID(), request.getDeviceID())) {
            return new ResponseEntity<>(request.getDeviceID().toString(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(request.getDeviceID().toString(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/connect")
    public ResponseEntity<Void> connectDeviceToUser(@RequestBody DeviceUserDto deviceUserDto) throws JsonProcessingException {
        deviceConnectivityService.connectDevice(deviceUserDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disconnect")
    public ResponseEntity<String> disconnectDeviceFromUser(@RequestBody DeviceUserDto deviceUserDto) {
        deviceConnectivityService.disconnectDevice(deviceUserDto);
        return ResponseEntity.ok().body("result");
    }
}

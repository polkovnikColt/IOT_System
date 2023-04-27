package com.example.devicetouserservice.devicetouser;

import com.example.devicetouserservice.devicetouser.dto.DeviceUserDeleteRequest;
import com.example.devicetouserservice.devicetouser.dto.DeviceUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deviceToUser")
public class DeviceConnectivityController {

    private static final String KEY = ".v2";
    private Jedis jedis = new Jedis("localhost", 6379);
    private final DeviceConnectivityService deviceConnectivityService;

    public DeviceConnectivityController(DeviceConnectivityService deviceConnectivityService) {
        this.deviceConnectivityService = deviceConnectivityService;
    }

    @GetMapping("/getDevice/{id}")
    public ResponseEntity<DeviceConnectivity> getDeviceConnectivityById(@PathVariable UUID id) {
        DeviceConnectivity deviceConnectivity = deviceConnectivityService.getDeviceConnectivityById(id);
        return new ResponseEntity<>(deviceConnectivity, HttpStatus.OK);
    }

    @PostMapping("/getDeviceById")
    public ResponseEntity<String> getDeviceById(@RequestBody DeviceUserDto deviceUserDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (jedis.exists(deviceUserDto.getUserID().toString() + KEY)) {
            System.out.println("from redis" + jedis.smembers(deviceUserDto.getUserID() + KEY));
            if (jedis.smembers(deviceUserDto.getUserID() + KEY).contains(deviceUserDto.getDeviceID().toString())) {
                return new ResponseEntity<>(deviceUserDto.getDeviceID().toString(), HttpStatus.OK);
            }
        }
        String deviceJson = deviceConnectivityService.getDeviceConnectivityById(deviceUserDto.getUserID()).getDevices();
        Set<String> devices = objectMapper.readValue(deviceJson, Set.class);
        if (devices.contains(deviceUserDto.getDeviceID().toString())) {
            // Return the device object in the response body
            return new ResponseEntity<>(deviceUserDto.getDeviceID().toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deviceUserDto.getDeviceID().toString(), HttpStatus.NOT_FOUND);
        }

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
    public ResponseEntity<String> disconnectDeviceFromUser(@RequestBody DeviceUserDto deviceUserDto) throws JsonProcessingException {
        deviceConnectivityService.disconnectDevice(deviceUserDto);
        return ResponseEntity.ok().body("result");
    }


    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Set<String>> getAllDevicesByUserId(@PathVariable UUID userId) throws JsonProcessingException {
        Set<String> devices = deviceConnectivityService.getAllDevices(userId);
        return ResponseEntity.ok(devices);
    }
}

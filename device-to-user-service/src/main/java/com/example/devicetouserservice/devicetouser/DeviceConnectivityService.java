package com.example.devicetouserservice.devicetouser;

import com.example.devicetouserservice.devicetouser.dto.DeviceRequest;
import com.example.devicetouserservice.devicetouser.dto.DeviceUserDto;
import com.example.devicetouserservice.devicetouser.kafka.ConnectionProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceConnectivityService {

    private static final String KEY = ".v2";
    private Jedis jedis = new Jedis("localhost", 6379);
    private final DeviceConnectivityRepository deviceConnectivityRepository;
    private final ConnectionProducer connectionProducer;

    public DeviceConnectivityService(DeviceConnectivityRepository deviceConnectivityRepository, ConnectionProducer connectionProducer) {
        this.deviceConnectivityRepository = deviceConnectivityRepository;
        this.connectionProducer = connectionProducer;
    }

    public void createDeviceConnectivity(UUID userID) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(new ArrayList<>());
        deviceConnectivityRepository.save(new DeviceConnectivity(userID, jsonString));
    }

    public Set<String> getAllDevices(UUID id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("GET ALL " + id);
        if (jedis.exists(id.toString() + KEY)) {
            System.out.println("from redis" + jedis.smembers(id + KEY));
            return jedis.smembers(id + KEY);
        }
        Set<String> devices = objectMapper.readValue(getDeviceConnectivityById(id).getDevices(), Set.class);
        return devices;
    }

    public DeviceConnectivity getDeviceConnectivityById(UUID id) {
        return deviceConnectivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DeviceConnectivity not found with id: " + id));
    }


    public void deleteDeviceConnectivityById(UUID id) {
        deviceConnectivityRepository.deleteById(id);
    }

    public boolean deleteDeviceForUser(UUID userId, UUID deviceId) throws JsonProcessingException {
        DeviceConnectivity deviceConnectivity = deviceConnectivityRepository.getReferenceById(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> devices = objectMapper.readValue(deviceConnectivity.getDevices(), Set.class);
        if (devices.contains(deviceId.toString())) {
            RestTemplate restTemplate = new RestTemplate();
            String registrationUrl = "http://localhost:9001/api/v1/device/delete/" + deviceId;
            HttpEntity<String> requestEntity = new HttpEntity<>(null);

            ResponseEntity<String> response = restTemplate.exchange(registrationUrl, HttpMethod.DELETE, requestEntity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Cannot get device for deviceID: " + deviceId);
            }
            deviceConnectivity.setDevices(objectMapper
                    .writeValueAsString(devices.stream().filter(d -> !d.equals(deviceId.toString())).toList()));
            deviceConnectivityRepository.save(deviceConnectivity);
            return true;
        } else {
            return false;
        }
    }

    public void connectDevice(DeviceUserDto deviceUserDto) {
        DeviceConnectivity deviceConnectivity = getDeviceConnectivityById(deviceUserDto.getUserID());
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> devices;
        try {
            devices = objectMapper.readValue(deviceConnectivity.getDevices(), Set.class);

            if (devices.contains(deviceUserDto.getDeviceID().toString())) {
                connectionProducer.sendChangeStatus(deviceUserDto.getDeviceID().toString());
            } else {
                RestTemplate restTemplate = new RestTemplate();

                String registrationUrl = "http://localhost:9002/api/v1/deviceRegistr/" + deviceUserDto.getDeviceID();
                HttpEntity<String> requestEntity = new HttpEntity<>(null);

                ResponseEntity<String> response = restTemplate.exchange(registrationUrl, HttpMethod.GET, requestEntity, String.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("Cannot get device registration for deviceID: " + deviceUserDto.getDeviceID());
                }


                String deviceUrl = "http://localhost:9001/api/v1/device/status/" + deviceUserDto.getDeviceID();
                requestEntity = new HttpEntity<>(null);

                response = restTemplate.exchange(deviceUrl, HttpMethod.GET, requestEntity, String.class);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("Cannot get device for deviceID: " + deviceUserDto.getDeviceID());
                }
                Map<String, String> responseBodyMap = new ObjectMapper().readValue(response.getBody(), Map.class);

                String status = responseBodyMap.get("status");
                if (status.equals("ONLINE")) {
                    devices.add(deviceUserDto.getDeviceID().toString());
                    deviceConnectivity.setDevices(objectMapper.writeValueAsString(devices));
                    deviceConnectivityRepository.save(deviceConnectivity);
                    String updatedDevices = objectMapper.writeValueAsString(devices);
                    jedis.sadd(deviceUserDto.getUserID().toString() + KEY, devices.toArray(new String[0]));
                    jedis.expire(deviceUserDto.getDeviceID().toString() + KEY, 60);
                }
            }
        } catch (Exception e) {

        }
    }

    public void disconnectDevice(DeviceUserDto deviceUserDto) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        DeviceRequest device = new DeviceRequest(deviceUserDto.getDeviceID(), "OFFLINE");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DeviceRequest> requestEntity = new HttpEntity<>(device, headers);

        String url = "http://localhost:9001/api/v1/device/changeStatus";
        ResponseEntity<DeviceRequest> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, DeviceRequest.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            DeviceConnectivity deviceConnectivity = deviceConnectivityRepository.getReferenceById(deviceUserDto.getUserID());
            ObjectMapper objectMapper = new ObjectMapper();
            Set<String> devices = objectMapper.readValue(deviceConnectivity.getDevices(), Set.class);
            String updatedDevices = objectMapper.writeValueAsString(devices.stream()
                    .filter(s -> s.equals(deviceUserDto.getDeviceID().toString())).collect(Collectors.toSet()));
            deviceConnectivity.setDevices(objectMapper.writeValueAsString(updatedDevices));
            deviceConnectivityRepository.save(deviceConnectivity);
            jedis.sadd(deviceUserDto.getUserID().toString() + KEY, devices.toArray(new String[0]));
            jedis.expire(deviceUserDto.getDeviceID().toString() + KEY, 60);
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new RuntimeException("not found");
        } else {
            throw new RuntimeException("Error");
        }
    }
}
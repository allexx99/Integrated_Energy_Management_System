package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.services.impl;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.dtos.DeviceDTO;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Device;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Measurement;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.repositories.DeviceRepo;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.services.DeviceServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DeviceService implements DeviceServiceInterface {

    private final DeviceRepo deviceRepo;

    @Autowired
    public DeviceService(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    @RabbitListener(queues = "device-queue")
    public void processDeviceMessage(String jsonMessage) {
        // Process the received message (e.g., log it or perform some action)
        // System.out.println("Received message from device-queue: " + message);
        // Deserialize the JSON message into a Map
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Use TypeReference to specify the target type
            Map<String, String> deviceData = objectMapper.readValue(jsonMessage, new TypeReference<Map<String, String>>() {});

            // Extract data from the Map
            String CRUD_Type = deviceData.get("CRUD_Type");
            String deviceId = deviceData.get("device_id");
            String deviceName = deviceData.get("device_name");
            String userId = deviceData.get("user_id");

            // Process the received data (e.g., log it or perform some action)
            System.out.println("Received message from device-queue:");
            System.out.println("CRUD_Type: " + CRUD_Type);
            System.out.println("Device ID: " + deviceId);
            System.out.println("Device Name: " + deviceName);
            System.out.println("User ID: " + userId);

            if(CRUD_Type.equals("create")) {
                addDevice(Long.parseLong(deviceId), deviceName, Long.parseLong(userId));
            } else if (CRUD_Type.equals("delete")) {
                deleteDevice(Long.parseLong(deviceId));
            } else if (CRUD_Type.equals("update")) {
                updateDevice(Long.parseLong(deviceId), deviceName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing device message", e);
        }
    }

    @Override
    public Device addDevice(long id, String name, long userId) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(id);
        deviceDTO.setName(name);
        deviceDTO.setMeasurementList(new ArrayList<>());
        deviceDTO.setUserId(userId);
        Device device = deviceDTO.convertToEntity(deviceDTO);
        return deviceRepo.save(device);
    }

    @Override
    public void deleteDevice(long id) {
        Device device = deviceRepo.findDeviceById(id);
        deviceRepo.delete(device);
    }

    @Override
    public void updateDevice(long id, String name) {
        Device device = deviceRepo.findDeviceById(id);
        device.setName(name);
        deviceRepo.save(device);
    }

    @Override
    public List<Measurement> getMeasurements(long id) {
        Device device = deviceRepo.findDeviceById(id);
        return device.getMeasurementList();
    }
}

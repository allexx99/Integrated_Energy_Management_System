package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.services.impl;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Device;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Measurement;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.repositories.DeviceRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MeasurementService {

    private final DeviceRepo deviceRepo;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MeasurementService(DeviceRepo deviceRepo, SimpMessagingTemplate messagingTemplate) {
        this.deviceRepo = deviceRepo;
        this.messagingTemplate = messagingTemplate;
    }

    @Transactional
    @RabbitListener(queues = "myQueue")
    public void processMeasurementMessage(String jsonMessage) {
        // Deserialize the JSON message into a Map
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> deviceData = objectMapper.readValue(jsonMessage, Map.class);

            // Extract data from the Map
            long timestamp = (long) deviceData.get("timestamp");
            String deviceId = (String) deviceData.get("device_id");
            double measurementValue = (double) deviceData.get("measurement_value");

            // Process the received data (e.g., log it or perform some action)
            System.out.println("Received message from myQueue:");
            System.out.println("Timestamp: " + timestamp);
            System.out.println("Device ID: " + deviceId);
            System.out.println("Measurement Value: " + measurementValue);

            Measurement measurement = new Measurement();
            measurement.setTimestamp(String.valueOf(timestamp));
            measurement.setMeasurement_value(measurementValue);

            System.out.println();
            System.out.println("New Measurement:");
            System.out.println(measurement.getTimestamp() + ", " + measurement.getMeasurement_value());
            System.out.println();

            Device device = deviceRepo.findDeviceById(Long.parseLong(deviceId));
            System.out.println("User's id of this device is " + device.getUserId());

            if (measurement.getMeasurement_value() > 20 && measurement.getMeasurement_value() <= 22) {
                String notification = createJsonMessage("Device " + deviceId + " Warning: " + "Value exceeded the limit!");
                messagingTemplate.convertAndSend("/topic/notification/" + device.getUserId(), notification);
            }

            saveToMyMeasurementList(Long.parseLong(deviceId), measurement);
        } catch (Exception e) {
            throw new RuntimeException("Error processing device message", e);
        }
    }

    private static String createJsonMessage(String message) {

        JsonObject json = new JsonObject();
        // json.addProperty("deviceId", deviceId);
        json.addProperty("message", message);

        return new Gson().toJson(json);
    }

    public void saveToMyMeasurementList(long id, Measurement measurement) {
        Device device = deviceRepo.findDeviceById(id);
        if (device != null) {
            device.getMeasurementList().add(measurement);
            deviceRepo.save(device);
        } else {
            System.out.println("No such device in the database");
        }

    }
}

package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.controllers;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Measurement;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.services.impl.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /*@GetMapping(value = "/getMeasurements/{deviceId}")
    public List<Measurement> getMeasurements(@PathVariable long deviceId) {
        return deviceService.getMeasurements(deviceId);
    }*/
    @GetMapping(value = "/getMeasurements/{deviceId}")
    public ResponseEntity<List<Measurement>> getMeasurements(@PathVariable long deviceId) {
        List<Measurement> measurements = deviceService.getMeasurements(deviceId);
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }
}

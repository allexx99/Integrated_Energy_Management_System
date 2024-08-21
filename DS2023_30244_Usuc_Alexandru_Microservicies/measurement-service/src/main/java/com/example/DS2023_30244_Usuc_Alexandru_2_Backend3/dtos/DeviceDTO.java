package com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.dtos;

import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Device;
import com.example.DS2023_30244_Usuc_Alexandru_2_Backend3.entities.Measurement;

import java.util.List;

public class DeviceDTO {

    private long id;

    private String name;

    List<Measurement> measurementList;

    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }

    public Device convertToEntity(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setId(deviceDTO.getId());
        device.setName(deviceDTO.getName());
        device.setMeasurementList(deviceDTO.getMeasurementList());
        device.setUserId(deviceDTO.getUserId());
        return device;
    }
}

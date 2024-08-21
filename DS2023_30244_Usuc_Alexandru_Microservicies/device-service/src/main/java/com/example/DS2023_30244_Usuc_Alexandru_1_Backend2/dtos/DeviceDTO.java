package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.Device;

public class DeviceDTO {

    private long id;
    private String name;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Device convertToEntity(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setId(deviceDTO.getId());
        device.setName(deviceDTO.getName());
        device.setType(deviceDTO.getType());
        return device;
    }
}

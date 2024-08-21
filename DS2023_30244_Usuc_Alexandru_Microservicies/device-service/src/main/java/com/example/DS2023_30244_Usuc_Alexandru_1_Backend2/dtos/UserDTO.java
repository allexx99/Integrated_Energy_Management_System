package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.Device;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.User;

import java.util.List;

public class UserDTO {

    private long id;
    private String username;
    private List<Device> deviceList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setDeviceList(userDTO.getDeviceList());
        return user;
    }
}

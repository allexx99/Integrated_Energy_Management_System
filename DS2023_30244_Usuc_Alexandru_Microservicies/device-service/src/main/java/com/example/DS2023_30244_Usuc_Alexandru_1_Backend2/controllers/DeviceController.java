package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.controllers;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.DeviceDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.UserDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.Device;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.servicies.impl.DeviceService;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.servicies.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DeviceController {

    private final DeviceService deviceService;
    private final UserService userService;

    @Autowired
    public DeviceController(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    // --------------------------- device --------------------------- //

    @GetMapping(value = "/helloUser")
    public String Hello() {
        return "Hi there! You called device microservice hello function";
    }

    /*@PostMapping(value = "/saveDevice")
    public String saveDevice(@RequestBody DeviceDTO deviceDTO) {
        deviceService.addDevice(deviceDTO);
        return "Saved...";
    }*/
    @PostMapping(value = "/saveDevice")
    public ResponseEntity<?> saveDevice(@RequestBody DeviceDTO deviceDTO) {
        deviceService.addDevice(deviceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*@GetMapping(value = "/readDevices")
    public List<DeviceDTO> getDevices() {
        return deviceService.getDevices();
    }*/
    @GetMapping(value = "/readDevices")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> devices = deviceService.getDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    /*@GetMapping(value = "/readDevices/{id}")
    public DeviceDTO getDeviceById(@PathVariable long id) {
        return deviceService.getDeviceById(id);
    }*/
    @GetMapping(value = "/readDevices/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable long id) {
        DeviceDTO deviceDTO = deviceService.getDeviceById(id);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    /*@PutMapping(value = "/updateDevice/{id}")
    public String updateDevice(@PathVariable long id, @RequestBody DeviceDTO deviceDTO) {
        deviceService.updateDevice(id,deviceDTO);
        return "Devices with id " + id + " was updated";
    }*/
    @PutMapping(value = "/updateDevice/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable long id, @RequestBody DeviceDTO deviceDTO) {
        deviceService.updateDevice(id,deviceDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@DeleteMapping(value = "/deleteDevice/{id}")
    public String deleteDevice(@PathVariable long id) {
        deviceService.deleteDevice(id);
        return "Device with id " + id + " was deleted";
    }*/
    @DeleteMapping(value = "/deleteDevice/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable long id) {
        deviceService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@PostMapping(value = "/saveToMyDeviceList/{id}")
    public void saveToMyDeviceList(@PathVariable long id, @RequestBody DeviceDTO deviceDTO) {
        deviceService.saveToMyDeviceList(id, deviceDTO);
    }*/
    @PostMapping(value = "/saveToMyDeviceList/{id}")
    public ResponseEntity<?> saveToMyDeviceList(@PathVariable long id, @RequestBody DeviceDTO deviceDTO) {
        deviceService.saveToMyDeviceList(id, deviceDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // --------------------------- device --------------------------- //

    // ---------------------------- user ---------------------------- //

    /*@PostMapping(value = "/saveUserToDeviceDB")
    public String saveUserToDeviceDB(@RequestBody UserDTO userDTO) {
        userService.addUserToDeviceDB(userDTO);
        return "User saved...";
    }*/

    /*@PostMapping(value = "/saveUserToDeviceDB/{id}/{username}")
    public String saveUserToDeviceDB(@PathVariable long id, @PathVariable String username) {
        userService.addUserToDeviceDB(id, username);
        return "User saved...";
    }*/
    @PostMapping(value = "/saveUserToDeviceDB/{id}/{username}")
    public ResponseEntity<?> saveUserToDeviceDB(@PathVariable long id, @PathVariable String username) {
        userService.addUserToDeviceDB(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@DeleteMapping(value = "/deleteUserFromDeviceDB/{id}")
    public String deleteUserFromDeviceDB(@PathVariable long id) {
        userService.deleteUserFromDeviceDB(id);
        return "User with id " + id + " was deleted from device db";
    }*/
    @DeleteMapping(value = "/deleteUserFromDeviceDB/{id}")
    public ResponseEntity<?> deleteUserFromDeviceDB(@PathVariable long id) {
        userService.deleteUserFromDeviceDB(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@PutMapping(value = "/updateUserFromDeviceDB/{id}")
    public String updateUserFromDeviceDB(@PathVariable long id, @RequestBody UserDTO userDTO) {
        userService.updateUserFromDeviceDB(id, userDTO);
        return "User updated...";
    }*/

    /*@PutMapping(value = "/updateUserFromDeviceDB/{id}/{username}")
    public String updateUserFromDeviceDB(@PathVariable long id, @PathVariable String username) {
        userService.updateUserFromDeviceDB(id, username);
        return "User updated...";
    }*/
    @PutMapping(value = "/updateUserFromDeviceDB/{id}/{username}")
    public ResponseEntity<?> updateUserFromDeviceDB(@PathVariable long id, @PathVariable String username) {
        userService.updateUserFromDeviceDB(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@GetMapping(value = "/getUserDevices/{id}")
    public List<DeviceDTO> getUserDevices(@PathVariable long id) {
        return userService.getUserDevices(id);
    }*/
    @GetMapping(value = "/getUserDevices/{id}")
    public ResponseEntity<List<DeviceDTO>> getUserDevices(@PathVariable long id) {
        List<DeviceDTO> devices = userService.getUserDevices(id);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    /*@GetMapping(value = "/getUsersId/{deviceId}")
    public String getUsersId(@PathVariable long deviceId) {
        return userService.getUsersId(deviceId);
    }*/
    @GetMapping(value = "/getUsersId/{deviceId}")
    public ResponseEntity<String> getUsersId(@PathVariable long deviceId) {
        return ResponseEntity.ok(userService.getUsersId(deviceId));
    }

    // ---------------------------- user ---------------------------- //
}

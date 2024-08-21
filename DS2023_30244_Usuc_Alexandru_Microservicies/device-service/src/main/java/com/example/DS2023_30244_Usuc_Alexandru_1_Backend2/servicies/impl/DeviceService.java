package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.servicies.impl;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.DeviceDTO;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.Device;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities.User;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.repositories.DeviceRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.repositories.UserRepo;
import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.servicies.DeviceServiceInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.Queue;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DeviceService implements DeviceServiceInterface {

    private final String RABBITMQ_URI = "amqps://cyzpinwn:R8Z_JF3EA2kzs5NNI1F7aQRkbjHyRvUp@gull.rmq.cloudamqp.com/cyzpinwn";
    private final static String QUEUE_NAME = "device-queue";
    private final RabbitTemplate rabbitTemplate;
    private final Queue deviceQueue;
    private final DeviceRepo deviceRepo;
    private final UserRepo userRepo;
    private final UserService userService;

    @Autowired
    public DeviceService(RabbitTemplate rabbitTemplate, Queue deviceQueue, DeviceRepo deviceRepo, UserRepo userRepo, UserService userService) {
        this.rabbitTemplate = rabbitTemplate;
        this.deviceQueue = deviceQueue;
        this.deviceRepo = deviceRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Device addDevice(DeviceDTO deviceDTO) {
        Device device = deviceDTO.convertToEntity(deviceDTO);
        deviceRepo.save(device);

        // Send the Device object to CloudAMQP
        // rabbitTemplate.convertAndSend("device-exchange", "device.queue", device);
        // Send a message directly to the "device-queue"
        // rabbitTemplate.convertAndSend(deviceQueue.getName(), "Another devices was added");

        String jsonMessage = createJsonMessage("create", String.valueOf(device.getId()), device.getName());
        // rabbitTemplate.convertAndSend(deviceQueue.getName(), jsonMessage.getBytes());

        try (Connection connection = createConnection(RABBITMQ_URI);
             Channel channel = connection.createChannel()) {

            // Declare the queue
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());

        } catch (IOException | TimeoutException | URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return device;
    }

    @Override
    public List<DeviceDTO> getDevices() {
        List<Device> devices = deviceRepo.findAll();
        List<DeviceDTO> deviceDTOS = new ArrayList<>();
        for(Device device : devices) {
            deviceDTOS.add(device.convertToDTO(device));
        }
        return deviceDTOS;
    }

    @Override
    public DeviceDTO getDeviceById(long id) {
        Device device = deviceRepo.findDeviceById(id);
        return device.convertToDTO(device);
    }

    @Override
    @Transactional
    public void updateDevice(long id, DeviceDTO deviceDTO) {
        Device device = deviceRepo.findDeviceById(id);
        device.setName(deviceDTO.getName());
        device.setType(deviceDTO.getType());
        deviceRepo.save(device);

        String jsonMessage = createJsonMessage("update", String.valueOf(device.getId()), device.getName());
        // rabbitTemplate.convertAndSend(deviceQueue.getName(), jsonMessage.getBytes());

        try (Connection connection = createConnection(RABBITMQ_URI);
             Channel channel = connection.createChannel()) {

            // Declare the queue
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());

        } catch (IOException | TimeoutException | URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteDevice(long id) {
        Device device = deviceRepo.findDeviceById(id);
        deviceRepo.delete(device);

        String jsonMessage = createJsonMessage("delete", String.valueOf(device.getId()), device.getName());
        // rabbitTemplate.convertAndSend(deviceQueue.getName(), jsonMessage.getBytes());

        try (Connection connection = createConnection(RABBITMQ_URI);
             Channel channel = connection.createChannel()) {

            // Declare the queue
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());

        } catch (IOException | TimeoutException | URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveToMyDeviceList(long id, DeviceDTO deviceDTO) {
        User user =  userRepo.findUserById(id);
        Device deviceEntity = deviceDTO.convertToEntity(deviceDTO);
        user.getDeviceList().add(deviceEntity);
        userRepo.save(user);

        String jsonMessage = createJsonMessage("create", String.valueOf(deviceEntity.getId()), deviceEntity.getName(), String.valueOf(id));
        // rabbitTemplate.convertAndSend(deviceQueue.getName(), jsonMessage.getBytes());

        try (Connection connection = createConnection(RABBITMQ_URI);
             Channel channel = connection.createChannel()) {

            // Declare the queue
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());

        } catch (IOException | TimeoutException | URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection(String uri) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        return factory.newConnection();
    }

    private String createJsonMessage(String CRUD_Type, String device_id, String device_name) {
        JsonObject json = new JsonObject();
        json.addProperty("CRUD_Type", CRUD_Type);
        json.addProperty("device_id", device_id);
        json.addProperty("device_name", device_name);

        return new Gson().toJson(json);
    }
    private String createJsonMessage(String CRUD_Type, String device_id, String device_name, String user_id) {
        JsonObject json = new JsonObject();
        json.addProperty("CRUD_Type", CRUD_Type);
        json.addProperty("device_id", device_id);
        json.addProperty("device_name", device_name);
        json.addProperty("user_id", user_id);

        return new Gson().toJson(json);
    }

}

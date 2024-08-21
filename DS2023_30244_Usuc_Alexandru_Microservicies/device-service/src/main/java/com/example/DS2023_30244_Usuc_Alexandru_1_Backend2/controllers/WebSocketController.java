/*
package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.management.Notification;

@Controller
public class WebSocketController {

    private final ObjectMapper objectMapper;

    public WebSocketController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public String sendNotification(@Payload Notification notification) {
        try {
            // Convert the Notification object to a JSON string
            String jsonMessage = objectMapper.writeValueAsString(notification);
            return jsonMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing notification";
        }
    }
}
*/

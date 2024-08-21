package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.rabbitmq.client.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.awt.desktop.QuitEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Main {
    private final static String QUEUE_NAME = "myQueue";
    // Connection details (replace with your actual values from the CloudAMQP website at the RabbitMQ details of your created connection)
    private final static String RABBITMQ_URI = "amqps://cyzpinwn:R8Z_JF3EA2kzs5NNI1F7aQRkbjHyRvUp@gull.rmq.cloudamqp.com/cyzpinwn";

    public static void main(String[] args) throws Exception {
        try (Connection connection = createConnection(RABBITMQ_URI);
             Channel channel = connection.createChannel()) {

            // Declare the queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Read data from CSV file
            try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/alexu/Desktop/Anul 4/Sem1/SD/DS2023_30244_Usuc_Alexandru/DS2023_30244_Usuc_Alexandru_1-Copy/RabbitMQMessageProducer - Copy/sensor_test.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    double measurementValue = Double.parseDouble(line);
                    String jsonMessage = createJsonMessage(measurementValue);

                    // Publish the message to the queue
                    channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());
                    System.out.println(" [x] Sent: " + jsonMessage);

                    // Sleep for 1 second
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static Connection createConnection(String uri) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        return factory.newConnection();
    }
    private static String createJsonMessage(double measurementValue) {
        long timestamp = System.currentTimeMillis();
        String deviceId = "32";

        JsonObject json = new JsonObject();
        json.addProperty("timestamp", timestamp);
        json.addProperty("device_id", deviceId);
        json.addProperty("measurement_value", measurementValue);

        return new Gson().toJson(json);
    }
}
package com.iot_backend_rfid.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public class MqttService implements MqttCallback {

    private MqttClient mqttClient;

    public MqttService() throws MqttException {
        // Kết nối đến broker MQTT
        String broker = "tcp://localhost:1883";
        String clientId = "SpringBootClient";
        mqttClient = new MqttClient(broker, clientId);
        mqttClient.setCallback(this);
        mqttClient.connect();

        mqttClient.subscribe("rfid/idcard");
//        mqttClient.subscribe("rfid/system");
//        mqttClient.subscribe("rfid/logs");

    }



    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String payload = new String(mqttMessage.getPayload());
        System.out.println("Received message: " + payload + " from topic: " + topic);
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}

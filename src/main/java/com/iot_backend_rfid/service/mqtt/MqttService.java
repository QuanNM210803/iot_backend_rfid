package com.iot_backend_rfid.service.mqtt;

import com.iot_backend_rfid.common.Constants;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MqttService implements MqttCallback {

    private MqttClient mqttClient;

    public MqttService() throws MqttException {

        String broker = Constants.TLS_MQTT_URL;
        String clientId = Constants.CLIENT_ID;

        mqttClient = new MqttClient(broker, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(Constants.USERNAME);
        options.setPassword(Constants.PASSWORD.toCharArray());
        options.setKeepAliveInterval(60);

        mqttClient.setCallback(this);
        mqttClient.connect(options);

        mqttClient.subscribe(Constants.TOPIC_IDCARD);

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String payload = new String(mqttMessage.getPayload());

        System.out.println("Received message: " + payload + " from topic: " + topic);

        String[] data=payload.split("&&");

        int roomid=Integer.parseInt(data[0]);
        String cardid=data[1];

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime=LocalDateTime.parse(data[2], formatter);

    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}

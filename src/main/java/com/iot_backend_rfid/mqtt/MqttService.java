package com.iot_backend_rfid.mqtt;

import com.iot_backend_rfid.common.Constants;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

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
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}

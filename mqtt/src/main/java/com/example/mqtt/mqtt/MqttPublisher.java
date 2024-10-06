package com.example.mqtt.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttPublisher {

    private final MqttGateWay mqttGateWay;

    public void publish(String message) {
        log.info("Publishing message: {} to default topic(spot)", message);
        mqttGateWay.sendToMqtt(message);
    }

    public void publish(String topic, String message) {
        log.info("Publishing message: {} to topic: {}", message, topic);
        mqttGateWay.sendToMqtt(topic, message);
    }
}

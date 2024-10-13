package com.example.mqtt.mqtt;

import com.example.mqtt.config.MqttChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttTopic1Handler {

    @ServiceActivator(inputChannel = MqttChannel.TOPIC1_CHANNEL)
    public void handleMessage(Message<?> message) {
        message.getHeaders().forEach((k, v) -> log.info("Header: {}={}", k, v));
        log.info("Received message in topic1: {}", message.getPayload());
    }
}

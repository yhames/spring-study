package com.example.mqtt.mqtt;

import com.example.mqtt.config.MqttChannel;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = MqttChannel.OUTBOUND_CHANNEL)
public interface MqttGateWay {

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String payload);
}

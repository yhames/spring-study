package com.example.mqtt.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqttSubscriber {

    private final MqttPahoMessageDrivenChannelAdapter mqttInbound;

    public void subscribe(String topic) {
        mqttInbound.addTopic(topic);
    }

    public void unsubscribe(String topic) {
        mqttInbound.removeTopic(topic);
    }
}

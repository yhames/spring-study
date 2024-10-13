package com.example.mqtt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttChannel {

    public static final String TOPIC1_CHANNEL = "topic1Channel";
    public static final String TOPIC2_CHANNEL = "topic2Channel";
    public static final String OUTBOUND_CHANNEL = "mqttOutboundChannel";

    @Bean
    public MessageChannel topic1Channel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel topic2Channel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
}

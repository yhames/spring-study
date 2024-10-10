package com.example.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Configuration
public class MqttInboundConfig {

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel topic1Channel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel topic2Channel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "topic1Channel")
    public MessageHandler topic1Handler() {
        return message -> {
            log.info("Topic1 Received message: {}", message.getPayload());
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "topic2Channel")
    public MessageHandler topic2Handler() {
        return message -> {
            log.info("Topic2 Received message: {}", message.getPayload());
        };
    }

    @Bean
    @Router(inputChannel = "mqttInputChannel")
    public HeaderValueRouter inbound() {
        HeaderValueRouter router = new HeaderValueRouter(MqttHeaders.RECEIVED_TOPIC);
        router.setChannelMapping("topic1", "topic1Channel");
        router.setChannelMapping("topic2", "topic2Channel");
        return router;
    }


}

package com.example.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
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
    public HeaderValueRouter inboundRouter() {
        HeaderValueRouter router = new HeaderValueRouter(MqttHeaders.RECEIVED_TOPIC);
        router.setChannelMapping("topic1", "topic1Channel");
        router.setChannelMapping("topic2", "topic2Channel");
        return router;
    }

    @Bean
    public MessageProducerSupport inboundAdapter(MqttPahoClientFactory mqttClientFactory,
                                                 MessageChannel mqttInputChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                MqttClient.generateClientId(), mqttClientFactory, "topic1", "topic2");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    @Bean
    public IntegrationFlow mqttInboundFlow(HeaderValueRouter inboundRouter) {
        return IntegrationFlow.from("mqttInputChannel")
                .channel(mqttInputChannel())
                .route(inboundRouter)
                .get();
    }
}

package com.example.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttAdapter {

    @Bean
    public MessageProducerSupport topic1Adapter(MqttPahoClientFactory mqttClientFactory,
                                                MessageChannel topic1Channel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                MqttClient.generateClientId(), mqttClientFactory, MqttTopics.TOPIC1);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(topic1Channel);
        return adapter;
    }

    @Bean
    public MessageProducerSupport topic2Adapter(MqttPahoClientFactory mqttClientFactory,
                                                MessageChannel topic2Channel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                MqttClient.generateClientId(), mqttClientFactory, MqttTopics.TOPIC2);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(topic2Channel);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = MqttChannel.OUTBOUND_CHANNEL)
    public MqttPahoMessageHandler outboundAdapter(MqttPahoClientFactory mqttClientFactory) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                MqttClient.generateClientId(), mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(1);
        return messageHandler;
    }
}

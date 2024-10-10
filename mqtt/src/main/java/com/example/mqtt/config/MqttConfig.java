package com.example.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Configuration
public class MqttConfig {

    public static final String MQTT_USERNAME = "username";
    public static final String MQTT_PASSWORD = "password";
    public static final String MQTT_BROKER_URL = "tcp://localhost:1883";

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
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
    public MqttPahoClientFactory mqttClientFactory() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{MQTT_BROKER_URL});
        options.setUserName(MQTT_USERNAME);
        options.setPassword(MQTT_PASSWORD.toCharArray());
        options.setAutomaticReconnect(true);

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter adapter(MqttPahoClientFactory mqttClientFactory,
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
    @Router(inputChannel = "mqttInputChannel")
    public HeaderValueRouter inbound() {
        HeaderValueRouter router = new HeaderValueRouter(MqttHeaders.RECEIVED_TOPIC);
        router.setChannelMapping("topic1", "topic1Channel");
        router.setChannelMapping("topic2", "topic2Channel");
        return router;
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
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler outBound(MqttPahoClientFactory mqttClientFactory) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                MqttClient.generateClientId(), mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(1);
        return messageHandler;
    }
}

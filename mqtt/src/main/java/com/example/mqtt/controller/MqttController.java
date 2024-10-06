package com.example.mqtt.controller;

import com.example.mqtt.mqtt.MqttPublisher;
import com.example.mqtt.mqtt.MqttSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class MqttController {

    private final MqttPublisher mqttPublisher;
    private final MqttSubscriber mqttSubscriber;


    @GetMapping("/publish")
    public void publish() {
        mqttPublisher.publish("this is a test message");
    }

    @GetMapping("/publish/{topic}")
    public void publishToTopic(@PathVariable String topic, @RequestParam String message) {
        mqttPublisher.publish(topic, message);
    }

    @GetMapping("/subscribe/{topic}")
    public void subscribeToTopic(@PathVariable String topic) {
        mqttSubscriber.subscribe(topic);
    }

    @GetMapping("/subscribe/{topic}/remove")
    public void removeSubscription(@PathVariable String topic) {
        mqttSubscriber.unsubscribe(topic);
    }
}

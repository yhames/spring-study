package com.example.mqtt.controller;

import com.example.mqtt.mqtt.MqttPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class MqttController {

    private final MqttPublisher mqttPublisher;


    @GetMapping("/publish")
    public void publish() {
        mqttPublisher.publish("this is a test message");
    }

    @GetMapping("/publish/{topic}")
    public void publishToTopic(@PathVariable String topic, @RequestParam String message) {
        mqttPublisher.publish(topic, message);
    }
}

package com.example.mqtt.controller;

import com.example.mqtt.mqtt.MqttPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class MqttController {

    private final MqttPublisher mqttPublisher;

    @GetMapping("/publish/{topic}")
    public void publish(@PathVariable String topic, @RequestParam String message) {
        mqttPublisher.publish(topic, message);
    }
}

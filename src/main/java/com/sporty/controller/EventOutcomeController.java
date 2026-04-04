package com.sporty.controller;

import com.sporty.model.EventOutcome;
import com.sporty.service.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventOutcomeController {

    private final KafkaProducerService producer;
    private final ObjectMapper mapper = new ObjectMapper();

    public EventOutcomeController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping
    public String publish(@RequestBody EventOutcome event) throws Exception {
        String json = mapper.writeValueAsString(event);
        producer.send(json);
        return "Sent to Kafka";
    }
}
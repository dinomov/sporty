package com.sporty.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic() {
        return new NewTopic("event-outcomes", 1, (short) 1);
    }
}

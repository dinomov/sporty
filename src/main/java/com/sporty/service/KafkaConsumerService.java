package com.sporty.service;

import com.sporty.model.Bet;
import com.sporty.repository.BetRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final BetRepository repository;
    private final RocketMQProducerService rocketMQProducer;

    private final ObjectMapper mapper = new ObjectMapper();

    public KafkaConsumerService(BetRepository repository, RocketMQProducerService rocketMQProducer) {
        this.repository = repository;
        this.rocketMQProducer = rocketMQProducer;
    }

    @KafkaListener(topics = "event-outcomes", groupId = "bet-group")
    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void consume(String message, Acknowledgment ack) {
        try {
            JsonNode json = mapper.readTree(message);
            String eventId = json.get("eventId").asText();

            // Fetch unsettled bets atomically
            List<Bet> bets = repository.findByEventIdAndSettledFalse(eventId);
            log.info("Unsettled bets for event {}: {}", eventId, bets.size());

            for (Bet bet : bets) {
                log.info("Sending bet {} to RocketMQ for settlement", bet);
                rocketMQProducer.sendBetSettlement(bet);
            }

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to process Kafka message: {}", message, e);
            throw new RuntimeException(e);
        }
    }
}
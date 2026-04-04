package com.sporty.service;

import com.sporty.model.Bet;
import com.sporty.repository.BetRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
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
    public void consume(String message) throws Exception {
        JsonNode json = mapper.readTree(message);
        String eventId = json.get("eventId").asText();

        List<Bet> bets = repository.findByEventIdAndSettledFalse(eventId);
        log.info("bets which should be settled: {}", bets);
        for (Bet bet : bets) {
            log.info("send to RocketMQ for final settlement: {}", bet);
            rocketMQProducer.sendBetSettlement(bet);
        }
    }
}
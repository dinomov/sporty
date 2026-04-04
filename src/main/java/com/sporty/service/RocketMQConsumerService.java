package com.sporty.service;

import com.sporty.model.Bet;
import com.sporty.repository.BetRepository;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "bet-settlements", consumerGroup = "bet-settlements-group")
public class RocketMQConsumerService implements RocketMQListener<Bet> {

    private static final Logger log = LoggerFactory.getLogger(RocketMQConsumerService.class);

    private final BetRepository repository;

    public RocketMQConsumerService(BetRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onMessage(Bet bet) {
        try {
            log.info("Received bet settlement from RocketMQ: {}", bet.getBetId());
            log.info("Settled {} for user {} on event {} with winner {}",
                    bet.getBetId(), bet.getUserId(), bet.getEventId(), bet.getEventWinnerId());

            bet.setSettled(true);
            repository.save(bet);
        } catch (Exception e) {
            log.error("Failed to process bet message", e);
        }
    }
}
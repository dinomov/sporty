package com.sporty.service;

import com.sporty.model.Bet;
import com.sporty.repository.BetRepository;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RocketMQMessageListener(topic = "bet-settlements", consumerGroup = "bet-settlements-group")
public class RocketMQConsumerService implements RocketMQListener<Bet> {

    private static final Logger log = LoggerFactory.getLogger(RocketMQConsumerService.class);

    private final BetRepository repository;

    public RocketMQConsumerService(BetRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @Retryable(
            value = {DataAccessException.class, RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void onMessage(Bet bet) {
        log.info("Processing bet {}", bet.getBetId());

        int updated = repository.markAsSettled(bet.getBetId());

        if (updated == 0) {
            log.warn("Bet already settled: {}", bet.getBetId());
            return;
        }

        log.info("Successfully settled bet {}", bet.getBetId());
    }
}
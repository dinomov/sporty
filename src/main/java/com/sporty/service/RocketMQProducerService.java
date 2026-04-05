package com.sporty.service;

import com.sporty.model.Bet;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RocketMQProducerService {
    private static final Logger log = LoggerFactory.getLogger(RocketMQProducerService.class);

    private RocketMQTemplate rocketMQTemplate;

    public RocketMQProducerService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void sendBetSettlement(Bet bet) {
        try {
            rocketMQTemplate.convertAndSend("bet-settlements", bet);
        } catch (Exception e) {
            log.error("Failed to send bet {} to RocketMQ", bet.getBetId(), e);
            throw e; // triggers retry
        }
    }
}
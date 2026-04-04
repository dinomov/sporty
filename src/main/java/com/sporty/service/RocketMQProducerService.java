package com.sporty.service;

import com.sporty.model.Bet;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

@Service
public class RocketMQProducerService {

    private RocketMQTemplate rocketMQTemplate;

    public RocketMQProducerService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void sendBetSettlement(Bet bet) {
        try {
            rocketMQTemplate.convertAndSend("bet-settlements", bet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
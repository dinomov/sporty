package com.sporty.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bet")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long betId;

    private String userId;
    private String eventId;
    private String eventMarketId;
    private String eventWinnerId;
    private double betAmount;
    private boolean settled;

    public Bet() {}

    public Long getBetId() { return betId; }
    public void setBetId(Long betId) { this.betId = betId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getEventMarketId() { return eventMarketId; }
    public void setEventMarketId(String eventMarketId) { this.eventMarketId = eventMarketId; }

    public String getEventWinnerId() { return eventWinnerId; }
    public void setEventWinnerId(String eventWinnerId) { this.eventWinnerId = eventWinnerId; }

    public double getBetAmount() { return betAmount; }
    public void setBetAmount(double betAmount) { this.betAmount = betAmount; }

    public boolean isSettled() { return settled; }
    public void setSettled(boolean settled) { this.settled = settled; }

    @Override
    public String toString() {
        return "Bet{" +
                "betId=" + betId +
                ", userId='" + userId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", eventMarketId='" + eventMarketId + '\'' +
                ", eventWinnerId='" + eventWinnerId + '\'' +
                ", betAmount=" + betAmount +
                ", settled=" + settled +
                '}';
    }
}
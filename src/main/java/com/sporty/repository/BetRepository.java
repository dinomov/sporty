package com.sporty.repository;

import com.sporty.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, String> {
    List<Bet> findByEventIdAndSettledFalse(String eventId);
}
package com.sporty.repository;

import com.sporty.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByEventIdAndSettledFalse(String eventId);

    @Modifying
    @Query("""
        UPDATE Bet b 
        SET b.settled = true 
        WHERE b.betId = :betId AND b.settled = false
    """)
    int markAsSettled(@Param("betId") Long betId);
}
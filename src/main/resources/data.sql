delete from bet;

INSERT INTO bet (bet_id, user_id, event_id, event_market_id, event_winner_id, bet_amount, settled) VALUES
('1', 'user1', 'event1', 'market1', 'teamA', 100, false),
('2', 'user2', 'event1', 'market1', 'teamB', 150, false),

('3', 'user3', 'event2', 'market1', 'teamA', 200, false),
('4', 'user4', 'event2', 'market1', 'teamB', 250, false),

('5', 'user5', 'event3', 'market1', 'teamC', 300, false),
('6', 'user6', 'event3', 'market1', 'teamA', 350, false),

('7', 'user7', 'event4', 'market1', 'teamB', 400, false),
('8', 'user8', 'event4', 'market1', 'teamC', 450, false),

('9', 'user9', 'event5', 'market1', 'teamA', 500, false),
('10', 'user10', 'event5', 'market1', 'teamB', 550, false);
# Sports Betting Settlement Trigger Service

**Author:** Dilshod
**Tech Stack:** Java, Spring Boot, Kafka, RocketMQ, PostgreSQL, Docker, Docker Compose, Maven

---

## Overview

This backend service simulates sports betting event outcome handling and bet settlement using **Kafka** and **RocketMQ**.

It provides:

* **API endpoint** to publish sports event outcomes to Kafka.
* **Kafka consumer** to process event outcomes and match them to bets.
* **RocketMQ producer** to send bet settlement messages.
* **RocketMQ consumer** to settle bets.
* **PostgreSQL database** to store bets.

---

## Features

1. **Publish Event Outcome to Kafka**

    * API endpoint: `/api/events`
    * Event payload:

      ```json
      {
        "eventId": "event1",
        "eventName": "Team A vs Team B",
        "eventWinnerId": "teamA"
      }
      ```
    * Publishes the event outcome to Kafka topic `event-outcomes`.

2. **Kafka Consumer**

    * Listens to `event-outcomes` topic.
    * Matches bets from the database to the event.
    * Sends eligible bets to RocketMQ for settlement.

3. **RocketMQ Producer**

    * Produces bet settlement messages to `bet-settlements`.

4. **RocketMQ Consumer**

    * Listens to `bet-settlements`.
    * Settles bets in the database.

5. **Database**

    * PostgreSQL stores bets with the following fields:

        * Bet ID
        * User ID
        * Event ID
        * Event Market ID
        * Event Winner ID
        * Bet Amount
        * Settled

---

## Prerequisites

* Docker & Docker Compose

---

## Getting Started

### 1. Clone the project

```
git clone https://github.com/dinomov/sporty.git

cd sporty
```

### 2. Run using the start script

```bash
./start.sh
```

This script will:

1. Build the Docker image `sports-betting:latest`.
2. Start all containers with Docker Compose: Kafka, RocketMQ, PostgreSQL, and the app.

### 3. Access the service

* API Base URL: `http://localhost:8080`
* Example: Publish an event outcome:

```bash

curl -X POST http://localhost:8080/api/events \
     -H "Content-Type: application/json" \
     -d '{"eventId”:”event1","eventName":"TeamA vs TeamB","eventWinnerId":"teamA"}'
    
```

---

## Stop services

```bash
docker compose down -v
```

---

## System Flow Diagram

```text
          +----------------+
          |  REST API      |
          |  /events/publish
          +--------+-------+
                   |
                   v
          +----------------+
          |  Kafka Topic   |
          |  event-outcomes|
          +--------+-------+
                   |
                   v
          +----------------+
          | Kafka Consumer |
          |  Processes     |
          |  Event Outcome |
          +--------+-------+
                   |
           Matches Event ID to Bets
                   |
                   v
          +----------------+
          | RocketMQ       |
          | Producer       |
          |  bet-settlements|
          +--------+-------+
                   |
                   v
          +----------------+
          | RocketMQ       |
          | Consumer       |
          |  Settles Bets  |
          +--------+-------+
                   |
                   v
          +----------------+
          | PostgreSQL DB  |
          |  Bets Updated  |
          +----------------+
```

**Flow Explanation:**

1. The **API endpoint** receives an event outcome and publishes it to the Kafka topic `event-outcomes`.
2. The **Kafka consumer** listens to `event-outcomes` and identifies bets in the database that match the Event ID.
3. Eligible bets are sent to **RocketMQ** via a producer to the topic `bet-settlements`.
4. The **RocketMQ consumer** processes the messages and settles bets in the database.
5. The **PostgreSQL database** stores updated bet results.

---

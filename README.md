# Streaming Twitter Feed
This project implements series of Java microservices that represent backend for Twitter feed

Here are some of the main features of the app:
* endpoint to add tweets
* endpoint to get feed(see existing and latest messages, without refreshing page)
* bot that automatically sending tweets with configurable delay(default - 2000 milliseconds for one tweet)
* endpoint start, stop, check status and configure bot

## Table of Contents

- [Technologies](#technologies)
- [Installation](#installation)
- [API](#api)

## Technologies

The project uses the following technologies and frameworks:

- Java
- Spring (Framework, Boot, MVC, Data JPA)
- Spring WebFlux (for HTTP Streaming)
- CockroachDB (cluster of 2 nodes as database)
- Lombok (boilerplate reduction)
- Maven (for dependency management)
- Docker (containerization platform)
- Git (for version control)

## Installation

To run whole project on docker containers, follow these steps:

Have installed Git, Docker and Postman.

1. Clone the repository:

   ```
   git clone https://github.com/YaremaMaksym/streaming-twitter-feed.git
   ```

2. Run start up file `start.sh` (it calls docker-compose.yaml, initialises cockroachdb cluster and creates db):
   - Locate project folder in file explorer.
   - Right click on him and choose "Git Bash Here".
   - Run `sh start.sh` in terminal.

3. Make POST request on `http://localhost:8082/api/v1/bot/start` with your Postman to start bot (`../stop` - to stop).

4. Open `feed.html` in your browser to see existing and latest tweets. 

## API
- Feed:
  - GET http://localhost:8080/api/v1/feed to **get feed** (existing and latest tweets).

- Tweet:
  - GET http://localhost:8081/api/v1/tweets to **get all tweets**.
  - POST http://localhost:8081/api/v1/tweets with JSON body as in example to **add tweet to db**:
      ```
      {
          "userId": 1,
          "content": "First tweet ever"
      }
      ```

- Bot: 
  - POST http://localhost:8082/api/v1/bot/start to **start bot**.
  - POST http://localhost:8082/api/v1/bot/stop to **stop bot**.
  - GET http://localhost:8082/api/v1/bot/status to **get bot status**.
  - POST http://localhost:8082/api/v1/bot/speed?intervalMilliseconds=5000 to **change bot speed**.
  - GET http://localhost:8082/api/v1/bot/speed to **get bot speed**. Default is 2000 milliseconds per message.

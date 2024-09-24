# Streaming Twitter Feed
This project implements series of Java microservices that represent backend for Twitter feed

Here are some of the main features of the app:
* endpoint to add tweets
* endpoint to get feed(see existing and latest messages, without refreshing page)
* bot that automatically sending tweets with configurable delay(default - 2000 milliseconds for one tweet)
* endpoint start, stop, check status and configure bot

## Table of Contents

- [Demo](#demo)
- [Technologies](#technologies)
- [Installation](#installation)
- [API](#api)

## Demo
Demonstration of feed page
![NVIDIA_Share_F5b2IcMkj9](https://github.com/YaremaMaksym/streaming-twitter-feed/assets/31901135/275fa6d2-8175-4848-a9f3-9f5b9a09a69e)
  
## Technologies

The project uses the following technologies and frameworks:

- Java
- Spring (Framework, Boot, MVC, Data JPA)
- Spring WebFlux (for HTTP Streaming)
- RabbitMQ (to queue messages between Tweet service and Feed)
- CockroachDB (as database - cluster of 2 nodes)
- Lombok (for boilerplate reduction)
- Maven (for project management)
- Docker (containerization platform)
- Git (for version control)

## Installation

To run whole project on docker containers, follow these steps:

Have installed Git and Docker. Postman - optional.

1. Clone the repository:

   ```
   git clone https://github.com/YaremaMaksym/streaming-twitter-feed.git
   ```

2. Run start up file `start.sh` (it calls docker-compose.yaml, initialises cockroachdb cluster and creates db):
   - Locate project folder in file explorer.
   - Right-click it and select "Git Bash Here".
   - Run `sh start.sh` in terminal.
   - Wait until all 5 containers start 

3. Open `feed.html` in your browser to see existing and latest tweets. 

## API
- Feed microservice:
  - GET http://localhost:8080/api/v1/feed to **get feed** (existing and latest tweets).

- Tweet microservice:
  - GET http://localhost:8081/api/v1/tweets to **get all tweets**.
  - POST http://localhost:8081/api/v1/tweets with JSON body as in example to **add tweet to db**:
      ```
      {
          "userId": 1,
          "content": "First tweet ever"
      }
      ```

- Bot microservice:
  - POST http://localhost:8082/api/v1/bot/stop to **stop bot**.
  - POST http://localhost:8082/api/v1/bot/start to **start bot** (it starts by default).
  - GET http://localhost:8082/api/v1/bot/status to **get bot status**.
  - POST http://localhost:8082/api/v1/bot/speed?intervalMilliseconds=5000 to **change bot speed**.
  - GET http://localhost:8082/api/v1/bot/speed to **get bot speed**. Default is 2000 milliseconds per message.

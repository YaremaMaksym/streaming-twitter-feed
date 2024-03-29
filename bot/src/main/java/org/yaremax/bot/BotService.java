package org.yaremax.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.yaremax.bot.util.TweetContentGenerator;
import org.yaremax.clients.tweet.TweetDto;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BotService {

    private final BotConfig botConfig;
    private final WebClient webClient;
    private ScheduledExecutorService scheduler;
    private final TweetContentGenerator tweetContentGenerator;

    public BotService(BotConfig botConfig, TweetContentGenerator tweetContentGenerator) {
        this.tweetContentGenerator = tweetContentGenerator;
        this.botConfig = botConfig;
        this.webClient = WebClient.create();
        this.scheduler = Executors.newScheduledThreadPool(1);

        startBot();
    }

    @Value("${tweet-service.host}")
    private String tweetServiceHost;

    public void startBot() {
        if (scheduler == null || scheduler.isShutdown() || scheduler.isTerminated()) {
            scheduler = Executors.newScheduledThreadPool(1);
        }

        scheduler.scheduleAtFixedRate(this::sendRandomTweet, 0, botConfig.getIntervalMilliseconds(), TimeUnit.MILLISECONDS);
        botConfig.setBotRunning(true);
    }

    public void stopBot() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Відновити статус переривання
            scheduler.shutdownNow();
        }

        botConfig.setBotRunning(false);
    }

    public String getBotStatus() {
        StringBuilder status = new StringBuilder();

        status.append("Right now bot is ");
        if (botConfig.isBotRunning()) {
            status.append("running.\n");
        }
        else {
            status.append("stopped.\n");
        }
        status.append("It posts a tweet every ")
                .append(botConfig.getIntervalMilliseconds())
                .append(" milliseconds");

        return status.toString();
    }

    public Long getBotIntervalMilliseconds() {
        return botConfig.getIntervalMilliseconds();
    }

    public void updateBotIntervalMilliseconds(Long intervalMilliseconds) {
        botConfig.setIntervalMilliseconds(intervalMilliseconds);

        if (botConfig.isBotRunning()) {
            stopBot();
            startBot();
        }
    }

    private void sendRandomTweet() {
        TweetDto tweetDto = tweetContentGenerator.generateTweet();
        sendTweet(tweetDto);
    }

    private void sendTweet(TweetDto tweetDto) {
        webClient.post()
                .uri("http://" + tweetServiceHost + ":8081/api/v1/tweets")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(tweetDto)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        result -> log.info("Tweet successfully sent"),
                        error -> log.error("Error sending tweet: " + error.getMessage())
                );
    }
}

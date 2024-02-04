package org.yaremax.feed.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.yaremax.feed.FeedService;
import org.yaremax.amqp.dto.TweetDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class TweetConsumer {

    private final FeedService feedService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consume(TweetDto tweetDto) {
        log.info("📦 Consumed {} from queue", tweetDto);

        feedService.receiveNewTweet(tweetDto);
    }
}

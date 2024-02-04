package org.yaremax.tweet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaremax.amqp.RabbitMQMessageProducer;
import org.yaremax.amqp.dto.TweetDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;
//
//    @Value("${rabbitmq.queue}")
//    private String queue;

//    TODO: paging
    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    public void addTweet(TweetDto tweetDto) {
        Tweet tweet = Tweet.builder()
                .userId(tweetDto.userId())
                .content(tweetDto.content())
                .createdAt(LocalDateTime.now())
                .build();

        tweetRepository.save(tweet);

        TweetDto tweetDtoWithTime = new TweetDto(
                tweet.getUserId(),
                tweet.getContent(),
                tweet.getCreatedAt()
        );

        rabbitMQMessageProducer.publish(
                exchange,
                routingKey,
                tweetDtoWithTime
        );
    }
}

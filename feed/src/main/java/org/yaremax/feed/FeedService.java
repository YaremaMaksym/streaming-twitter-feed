package org.yaremax.feed;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.yaremax.clients.tweet.TweetDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class FeedService {

    private volatile Sinks.Many<TweetDto> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final WebClient webClient = WebClient.create();

    @Value("${tweet-service.host}")
    private String tweetServiceHost;

    private synchronized void resetSink() {
        if (sink == null || sink.currentSubscriberCount() == 0) {
            sink = Sinks.many().multicast().onBackpressureBuffer();
        }
    }

    public void receiveNewTweet(TweetDto tweetDto) {
        sink.tryEmitNext(tweetDto);
    }

    public Flux<TweetDto> getTweetsStream() {
        resetSink();

        Flux<TweetDto> liveTweets = webClient.get()
                .uri("http://" + tweetServiceHost + ":8081/api/v1/tweets")
                .retrieve()
                .bodyToFlux(TweetDto.class);

        return Flux.merge(
                liveTweets,
                sink.asFlux()
        ).doOnCancel(this::resetSink);
    }

}

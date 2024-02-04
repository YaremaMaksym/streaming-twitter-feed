package org.yaremax.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yaremax.amqp.dto.TweetDto;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TweetDto> streamFeed() {
        return feedService.getTweetsStream();
    }

}

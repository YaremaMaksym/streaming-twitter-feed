package org.yaremax.tweet;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaremax.amqp.dto.TweetDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public ResponseEntity<List<Tweet>> getAllTweets() {
        return ResponseEntity.ok()
                .body(tweetService.getAllTweets());
    }

    @PostMapping
    public ResponseEntity<String> addTweet(@RequestBody TweetDto tweetDto) {
        tweetService.addTweet(tweetDto);

        return ResponseEntity.ok("Tweet was successfully added to db");
    }
}


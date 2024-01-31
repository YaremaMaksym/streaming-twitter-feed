package org.yaremax.tweet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;

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
    }
}

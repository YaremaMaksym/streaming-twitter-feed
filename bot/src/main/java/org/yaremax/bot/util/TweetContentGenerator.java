package org.yaremax.bot.util;

import org.springframework.stereotype.Component;
import org.yaremax.clients.tweet.TweetDto;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class TweetContentGenerator {
    private final Long userIdBound = 1000L;
    private final String[] subjects = { "I", "You", "He", "She", "We", "They" };
    private final String[] verbs = { "like", "dislike", "see", "use", "create", "love" };
    private final String[] objects = { "Java", "the new feature", "this app", "the latest update", "the tutorial" };
    private final String[] hashtags = { "#java", "#coding", "#tech", "#innovation", "#development" };

    private final Random random = new Random();

    private Long generateUserId() {
        return random.nextLong(userIdBound);
    }

    private String generateTweetContent() {
        String subject = subjects[random.nextInt(subjects.length)];
        String verb = verbs[random.nextInt(verbs.length)];
        String object = objects[random.nextInt(objects.length)];
        String hashtag = hashtags[random.nextInt(hashtags.length)];

        return subject + " " + verb + " " + object + " " + hashtag;
    }

    public TweetDto generateTweet() {
        return new TweetDto(
                generateUserId(),
                generateTweetContent(),
                LocalDateTime.now()
        );
    }
}

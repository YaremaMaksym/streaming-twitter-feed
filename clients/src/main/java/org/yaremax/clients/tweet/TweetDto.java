package org.yaremax.clients.tweet;

import java.time.LocalDateTime;

public record TweetDto(Long userId,
                       String content,
                       LocalDateTime createdAt) {
}

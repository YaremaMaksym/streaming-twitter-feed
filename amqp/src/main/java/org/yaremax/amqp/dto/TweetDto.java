package org.yaremax.amqp.dto;

import java.time.LocalDateTime;

public record TweetDto(Long userId,
                       String content,
                       LocalDateTime createdAt) {
}

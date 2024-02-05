package org.yaremax.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "org.yaremax.tweet",
                "org.yaremax.amqp",
                "org.yaremax.clients",
        }
)
public class TweetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetApplication.class, args);
    }

}

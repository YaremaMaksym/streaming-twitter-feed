package org.yaremax.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BotConfig {
    private long intervalMilliseconds = 2000;
}

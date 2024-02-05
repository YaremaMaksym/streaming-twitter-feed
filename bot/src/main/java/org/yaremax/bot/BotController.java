package org.yaremax.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bot")
@RequiredArgsConstructor
public class BotController {

    private final BotService botService;

    @PostMapping("/start")
    public ResponseEntity<String> startBot() {
        botService.startBot();
        return ResponseEntity.ok()
                .body("Bot started");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopBot() {
        botService.stopBot();
        return ResponseEntity.ok()
                .body("Bot stopped");
    }

    @GetMapping("/speed")
    public ResponseEntity<Long> getBotSpeed() {
        return ResponseEntity.ok()
                .body(botService.getBotIntervalMilliseconds());
    }

    @PostMapping("/speed")
    public ResponseEntity<String> updateBotSpeed(@RequestParam Long intervalMilliseconds) {
        botService.updateBotIntervalMilliseconds(intervalMilliseconds);
        return ResponseEntity.ok()
                .body("Successfully updated bot speed to " + intervalMilliseconds + " milliseconds");
    }
}

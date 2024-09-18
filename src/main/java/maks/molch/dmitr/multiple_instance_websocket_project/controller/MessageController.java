package maks.molch.dmitr.multiple_instance_websocket_project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
public class MessageController {

    @PostMapping(path = "/v1/{userId}")
    public void sendMessage(@RequestBody String message, @PathVariable String userId) {
        log.info("Message {} for user {}", message, userId);
    }
}

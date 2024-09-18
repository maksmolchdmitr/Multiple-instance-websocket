package maks.molch.dmitr.multiple_instance_websocket_project.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maks.molch.dmitr.multiple_instance_websocket_project.dto.Message;
import maks.molch.dmitr.multiple_instance_websocket_project.service.WebSocketService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Slf4j
public class WebSocketController {
    private final WebSocketService webSocketService;

    @MessageMapping("/message/{userId}")
    public Boolean sendMessage(
            Principal principal,
            @Header String authKey,
            @DestinationVariable String userId,
            @RequestBody Message message
    ) {
        log.info("Message {} received for user {}", message, userId);
        webSocketService.send(userId, message);
        return Boolean.TRUE;
    }
}

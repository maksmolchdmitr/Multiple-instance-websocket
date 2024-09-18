package maks.molch.dmitr.multiple_instance_websocket_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import maks.molch.dmitr.multiple_instance_websocket_project.dto.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketService {
    private final static ObjectMapper jsonMapper = new ObjectMapper();

    private final SimpMessagingTemplate messagingTemplate;

    @SneakyThrows
    public void send(String userId, Message message) {
        String json = jsonMapper.writeValueAsString(message);
        messagingTemplate.convertAndSendToUser(userId, "/topic/messages", json);
    }

}

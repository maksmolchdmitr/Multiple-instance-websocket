package maks.molch.dmitr.multiple_instance_websocket_project.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    static final String API_KEY_HEADER = "authKey";
    static final String SESSION_KEY_HEADER = "simpSessionId";
    static final String WS_ID_HEADER = "ws-id";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = readHeaderAccessor(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            String websocketId = readWebSocketIdHeader(accessor);
            String authKey = readAuthKeyHeader(accessor);
            String sessionId = accessor.getSessionId();

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    websocketId,
                    null
            );
            accessor.setUser(token);
            accessor.setHeader("connection-time", LocalDateTime.now().toString());
            log.info("User with authKey: [{}], WS-id: [{}] and session: [{}] make connection ", authKey, websocketId, sessionId);
        }
        return message;
    }

    private StompHeaderAccessor readHeaderAccessor(Message<?> message) {
        return Optional.ofNullable(MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class))
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Fail to read headers."));
    }

    private String readWebSocketIdHeader(StompHeaderAccessor accessor) {
        final String wsId = accessor.getFirstNativeHeader(WS_ID_HEADER);
        if (wsId == null || wsId.trim().isEmpty())
            throw new AuthenticationCredentialsNotFoundException("Web Socket ID Header not found");
        return wsId;
    }

    private String readAuthKeyHeader(StompHeaderAccessor accessor) {
        final String authKey = accessor.getFirstNativeHeader(API_KEY_HEADER);
        if (authKey == null || authKey.trim().isEmpty())
            throw new AuthenticationCredentialsNotFoundException("Auth Key Not Found");
        return authKey;
    }

    private String readSessionId(StompHeaderAccessor accessor) {
        return Optional.ofNullable(accessor.getMessageHeaders().get(SESSION_KEY_HEADER))
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Session header not found")).toString();
    }
}

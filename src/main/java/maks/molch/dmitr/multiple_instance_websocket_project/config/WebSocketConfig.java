package maks.molch.dmitr.multiple_instance_websocket_project.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maks.molch.dmitr.multiple_instance_websocket_project.config.props.BrokerConfig;
import maks.molch.dmitr.multiple_instance_websocket_project.interceptor.WebSocketChannelInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketChannelInterceptor webSocketChannelInterceptor;
    private final BrokerConfig brokerConfig;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws-register")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/queue", "/topic");
        registry.enableStompBrokerRelay("/queue", "/topic")
                .setRelayHost(brokerConfig.host())
                .setRelayPort(brokerConfig.port())
                .setClientLogin(brokerConfig.username())
                .setClientPasscode(brokerConfig.password())
                .setSystemLogin(brokerConfig.username())
                .setSystemPasscode(brokerConfig.password())
                .setUserDestinationBroadcast("/topic/unresolved-user")
                .setUserRegistryBroadcast("/topic/log-user-registry");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketChannelInterceptor);
    }
}

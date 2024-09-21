package maks.molch.dmitr.multiple_instance_websocket_project.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "websocket.broker")
public record BrokerConfig(
        String host,
        int port,
        String username,
        String password
) {
}

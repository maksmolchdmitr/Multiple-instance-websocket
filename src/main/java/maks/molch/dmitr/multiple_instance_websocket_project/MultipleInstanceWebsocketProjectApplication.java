package maks.molch.dmitr.multiple_instance_websocket_project;

import maks.molch.dmitr.multiple_instance_websocket_project.config.props.BrokerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(BrokerConfig.class)
@SpringBootApplication
public class MultipleInstanceWebsocketProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleInstanceWebsocketProjectApplication.class, args);
    }

}

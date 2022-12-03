package ar.nic.queues.natsdemo;


import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class NatsConfig {

    final String uri;

    Logger logger = LoggerFactory.getLogger(NatsConfig.class);

    @Autowired
    NatsConfig(@Value("${nats-server-uri:nats://127.0.0.1:4222}")  String uri){
        this.uri=uri;
    }

    @Bean
    public Connection initConnection() throws IOException {
        Options options = new Options.Builder()
                .name("Email notifications topic.")
                .errorCb(ex -> logger.error("Connection Exception: ", ex))
                .disconnectedCb(event -> logger.error("Channel disconnected: {}", event.getConnection()))
                .reconnectedCb(event -> logger.error("Reconnected to server: {}", event.getConnection()))
                .build();

        return Nats.connect(uri, options);
    }

}

package io.github.parkcheolu.netpotato.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${server.connection-thread-count:1}")
    private int connectionThreadCount;

    public int getServerPort() {
        return serverPort;
    }

    public int getConnectionThreadCount() {
        return connectionThreadCount;
    }
}

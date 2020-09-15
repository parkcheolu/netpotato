package io.github.parkcheolu.netpotato.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${server.port:8080}")
    int serverPort;

    @Value("${server.connection-thread-count:1}")
    int connectionThreadCount;

    @Value("${source.kafka.streams.application-id:source-streams-pipe}")
    String kafkaStreamsApplicationId;

    @Value("${source.kafka.bootstrap.server:10.13.253.144:9092}")
    String kafkaBootstrapServer;

    @Value("${source.kafka.input-topic:input-logs}")
    String kafkaSourceInputTopic;

    @Value("${source.kafka.output-topic:output-logs}")
    String kafkaSourceOutputTopic;

    public String getKafkaSourceInputTopic() {
        return kafkaSourceInputTopic;
    }
}

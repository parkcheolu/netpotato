package io.github.parkcheolu.netpotato.config;

import io.github.parkcheolu.netpotato.broadcasting.ClientChannelGroup;
import io.github.parkcheolu.netpotato.broadcasting.SimpleSourceBroadcast;
import io.github.parkcheolu.netpotato.broadcasting.SourceBroadcast;
import io.github.parkcheolu.netpotato.source.RandomNumberPublisher;
import io.github.parkcheolu.netpotato.source.SimpleFileContentPublisher;
import io.github.parkcheolu.netpotato.source.kafka.KafkaSourcePublisher;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.CorePublisher;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

@Configuration
public class BroadcastConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BroadcastConfiguration.class);

    @Autowired
    private ApplicationProperties properties;
    @Autowired
    private ClientChannelGroup clientChannelGroup;

    @Bean
    public SourceBroadcast broadcast() {
        SourceBroadcast broadcast = new SimpleSourceBroadcast();
        broadcast.setClient(clientChannelGroup);
        clientChannelGroup.addChannelGroupObserver(broadcast);
        return broadcast;
    }

//    @Bean
//    public CorePublisher sourcePublisher() throws Exception {
//        return new SimpleFileContentPublisher(Path.of("D:\\sample-2mb-text-file.txt"));
//    }

    @Bean
    public CorePublisher kafkaSourcePublisher() throws Exception {
        return new KafkaSourcePublisher();
    }

    @Bean
    public Properties kafkaConfiguration() {
        Properties kafkaProps = new Properties();
        kafkaProps.put(StreamsConfig.APPLICATION_ID_CONFIG, properties.kafkaStreamsApplicationId);
        kafkaProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, properties.kafkaBootstrapServer);
        kafkaProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        kafkaProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return kafkaProps;
    }
}

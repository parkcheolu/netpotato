package io.github.parkcheolu.netpotato.config;

import io.github.parkcheolu.netpotato.broadcasting.ClientChannelGroup;
import io.github.parkcheolu.netpotato.broadcasting.SimpleSourceBroadcast;
import io.github.parkcheolu.netpotato.broadcasting.SourceBroadcast;
import io.github.parkcheolu.netpotato.source.RandomNumberPublisher;
import io.github.parkcheolu.netpotato.source.SimpleFileContentPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.CorePublisher;

import java.io.IOException;
import java.nio.file.Path;

@Configuration
public class BroadcastConfiguration {

    @Autowired
    private ClientChannelGroup clientChannelGroup;

    @Bean
    public SourceBroadcast broadcast() {
        SourceBroadcast broadcast = new SimpleSourceBroadcast();
        broadcast.setClient(clientChannelGroup);
        clientChannelGroup.addChannelGroupObserver(broadcast);
        return broadcast;
    }

    @Bean
    public CorePublisher sourcePublisher() throws Exception {
        return new SimpleFileContentPublisher(Path.of("D:\\sample-2mb-text-file.txt"));
//        return new RandomNumberPublisher();
    }
}

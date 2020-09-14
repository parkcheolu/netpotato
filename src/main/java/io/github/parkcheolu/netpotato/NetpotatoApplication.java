package io.github.parkcheolu.netpotato;

import io.github.parkcheolu.netpotato.broadcasting.SourceBroadcast;
import io.github.parkcheolu.netpotato.server.NetpotatoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.CorePublisher;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class NetpotatoApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NetpotatoApplication.class);

    @Autowired
    NetpotatoServer netpotatoServer;

    @Autowired
    SourceBroadcast broadcast;

    @Autowired
    CorePublisher sourcePublisher;

    public static void main(String[] args) throws Exception {
        System.setProperty("server.port", "9999");
        SpringApplication.run(NetpotatoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Setting broadcasting source...");
        Schedulers.elastic().schedule(() -> {
            sourcePublisher.subscribe(broadcast);
        });
        logger.info("Starting NetpotatoServer...");
        netpotatoServer.run();
    }
}

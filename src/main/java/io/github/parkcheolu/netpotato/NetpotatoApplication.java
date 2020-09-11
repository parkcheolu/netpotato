package io.github.parkcheolu.netpotato;

import io.github.parkcheolu.netpotato.server.NetpotatoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetpotatoApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NetpotatoApplication.class);

    @Autowired
    NetpotatoServer netpotatoServer;

    public static void main(String[] args) throws Exception {
        System.setProperty("server.port", "9999");
        SpringApplication.run(NetpotatoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("run by command line");
        logger.info("Starting NetpotatoServer...");
        netpotatoServer.run();
    }
}

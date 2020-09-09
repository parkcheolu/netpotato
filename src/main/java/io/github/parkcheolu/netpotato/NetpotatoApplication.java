package io.github.parkcheolu.netpotato;

import io.github.parkcheolu.netpotato.server.NetpotatoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetpotatoApplication {

    private static final Logger logger = LoggerFactory.getLogger(NetpotatoApplication.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting NetpotatoServer...");
        NetpotatoServer server = new NetpotatoServer(9999);
        server.run();
    }
}

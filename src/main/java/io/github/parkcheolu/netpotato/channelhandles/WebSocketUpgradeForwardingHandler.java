package io.github.parkcheolu.netpotato.channelhandles;

import io.github.parkcheolu.netpotato.server.NetpotatoServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketUpgradeForwardingHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NetpotatoServer.class);
    private final String wsUri;

    public WebSocketUpgradeForwardingHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        logger.info("channelRead0 - uri: " + msg.uri());
        if (wsUri.equalsIgnoreCase(msg.uri())) {
            ctx.fireChannelRead(msg.retain());
        } else {
            ctx.channel().close();
        }
    }
}

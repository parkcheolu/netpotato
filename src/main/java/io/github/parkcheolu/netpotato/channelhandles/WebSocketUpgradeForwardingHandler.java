package io.github.parkcheolu.netpotato.channelhandles;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

public class WebSocketUpgradeForwardingHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;

    public WebSocketUpgradeForwardingHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if (wsUri.equalsIgnoreCase(msg.uri())) {
            ctx.fireChannelRead(msg.retain());
        }
    }
}

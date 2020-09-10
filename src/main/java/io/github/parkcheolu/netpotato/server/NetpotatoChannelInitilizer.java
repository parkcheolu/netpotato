package io.github.parkcheolu.netpotato.server;

import io.github.parkcheolu.netpotato.channelhandles.TextWebSocketFrameHandler;
import io.github.parkcheolu.netpotato.channelhandles.WebSocketUpgradeForwardingHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NetpotatoChannelInitilizer extends ChannelInitializer<Channel> {

    private final ChannelGroup channelGroup;

    public NetpotatoChannelInitilizer(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new HttpServerCodec())
                .addFirst(new LoggingHandler(LogLevel.INFO))
                .addLast(new HttpObjectAggregator(64 * 1024))
                .addLast(new WebSocketUpgradeForwardingHandler("/ws"))
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                .addLast(new TextWebSocketFrameHandler(channelGroup));

    }
}

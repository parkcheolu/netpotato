package io.github.parkcheolu.netpotato.broadcasting;

import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSourceBroadcast implements SourceBroadcast {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBroadcast.class);
    private ChannelGroup client;
    private Subscription subscription;
    private boolean active;

    @Override
    public void setClient(ChannelGroup clientChannelGroup) {
        this.client = clientChannelGroup;
    }

    @Override
    public void dispatch(Object msg) {
        if (! client.isEmpty()) {
            client.writeAndFlush(new TextWebSocketFrame(msg.toString()));
        }
    }

    @Override
    public void onSubscribe(Subscription s) {
        logger.info("onSubscribe - {}", s);
        this.subscription = s;
    }

    @Override
    public void onNext(Object o) {
        dispatch(o);
        request();
    }

    private void request() {
        if (! client.isEmpty() && ! active) {
            subscription.request(256);
            active = true;
        } else {
            active = false;
//            subscription.cancel();
        }
    }

    @Override
    public void onError(Throwable t) {
        logger.error("ERROR", t);
    }

    @Override
    public void onComplete() {
        logger.info("onComplete");
    }

    @Override
    public void setChannelGroup(ChannelGroup channelGroup) {
    }

    @Override
    public void channelUpdated() {
        logger.info("channelUpdated - " + client.size());
        request();
    }
}

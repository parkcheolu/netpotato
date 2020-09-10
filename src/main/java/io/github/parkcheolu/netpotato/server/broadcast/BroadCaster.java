package io.github.parkcheolu.netpotato.server.broadcast;

import io.github.parkcheolu.netpotato.source.SourceFetcher;
import io.netty.channel.group.ChannelGroup;

import java.util.concurrent.Callable;

public class BroadCaster implements Callable<Void> {

    private final ChannelGroup channelGroup;
    private final SourceFetcher sourceFetcher;

    public BroadCaster(ChannelGroup channelGroup, SourceFetcher sourceFetcher) {
        this.channelGroup = channelGroup;
        this.sourceFetcher = sourceFetcher;
    }

    @Override
    public Void call() throws Exception {
        sourceFetcher.fetch();
        return null;
    }
}

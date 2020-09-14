package io.github.parkcheolu.netpotato.broadcasting;

import io.netty.channel.group.ChannelGroup;

public interface ChannelGroupObserver {

    void setChannelGroup(ChannelGroup channelGroup);

    void channelUpdated();
}

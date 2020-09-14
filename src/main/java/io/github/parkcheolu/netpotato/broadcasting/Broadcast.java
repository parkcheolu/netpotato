package io.github.parkcheolu.netpotato.broadcasting;

import io.netty.channel.group.ChannelGroup;

public interface Broadcast {

    void setClient(ChannelGroup clientChannelGroup);

    void dispatch(Object msg);
}

package io.github.parkcheolu.netpotato.broadcasting;

import reactor.core.CoreSubscriber;

public interface SourceBroadcast extends Broadcast, CoreSubscriber, ChannelGroupObserver {
}
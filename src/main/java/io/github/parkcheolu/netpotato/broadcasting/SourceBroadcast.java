package io.github.parkcheolu.netpotato.broadcasting;

import io.github.parkcheolu.netpotato.source.SourcePublisher;
import io.github.parkcheolu.netpotato.source.SourceSubscriber;

public interface SourceBroadcast extends Broadcast, SourceSubscriber {

    void setSourcePublisher(SourcePublisher sourcePublisher);

}
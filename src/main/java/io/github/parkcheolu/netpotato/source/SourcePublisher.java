package io.github.parkcheolu.netpotato.source;

public interface SourcePublisher<S extends Source> {

    void subscribe(SourceSubscriber sourceSubscriber);
}

package io.github.parkcheolu.netpotato.source;

import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleFileContentPublisher implements CorePublisher<String> {

    private static final Logger logger = LoggerFactory.getLogger(SimpleFileContentPublisher.class);
    private final Path path;
    private final Flux<String> fileContentFlux;

    public SimpleFileContentPublisher(Path source) throws IOException {
        this.path = source;
        this.fileContentFlux = Flux.fromStream(Files.lines(path));
    }

    @Override
    public void subscribe(CoreSubscriber<? super String> subscriber) {
        subscribe((Subscriber<? super String>) subscriber);
    }

    @Override
    public void subscribe(Subscriber<? super String> s) {
        fileContentFlux
                .log()
                .subscribe(s);
    }
}

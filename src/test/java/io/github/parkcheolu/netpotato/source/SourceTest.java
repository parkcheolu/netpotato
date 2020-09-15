package io.github.parkcheolu.netpotato.source;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class SourceTest {

    @Test
    public void randomNumberTest() throws InterruptedException {
        AtomicLong counter = new AtomicLong();
        Flux.create(sink -> {
            IntStream.range(0, 1000).forEach(i -> sink.next(i));
        }, FluxSink.OverflowStrategy.LATEST).log()
        .subscribeOn(Schedulers.elastic())
                .publishOn(Schedulers.elastic())
                .subscribe(new SimpleSubscriber());
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void fileSourceTest() throws Exception {

    }
}

package io.github.parkcheolu.netpotato.source;

import org.reactivestreams.Subscriber;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberPublisher implements CorePublisher<Object> {

    @Override
    public void subscribe(CoreSubscriber<? super Object> subscriber) {
        subscribe((Subscriber<? super Object>) subscriber);
    }

    @Override
    public void subscribe(Subscriber<? super Object> s) {
        Flux.create(sink -> {
            while (true) {
                sink.next(ThreadLocalRandom.current().nextLong());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).log()
                .subscribe(s);
    }
}

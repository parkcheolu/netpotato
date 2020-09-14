package io.github.parkcheolu.netpotato.source;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;

public class SimpleSubscriber implements CoreSubscriber {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        subscription.request(1);
    }

    @Override
    public void onNext(Object o) {
        System.out.println(o);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println(t.toString());
        subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("complete");
    }
}

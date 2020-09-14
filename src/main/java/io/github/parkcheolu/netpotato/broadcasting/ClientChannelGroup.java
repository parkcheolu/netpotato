package io.github.parkcheolu.netpotato.broadcasting;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ClientChannelGroup implements ChannelGroup {

    private final ChannelGroup delegate;
    private final Set<ChannelGroupObserver> channelGroupObservers = ConcurrentHashMap.newKeySet();

    public ClientChannelGroup(ChannelGroup delegate) {
        this.delegate = delegate;
    }

    public void addChannelGroupObserver(ChannelGroupObserver channelGroupObserver) {
        channelGroupObservers.add(channelGroupObserver);
    }

    public void notifyChannelGroupChanges() {
        if (channelGroupObservers.isEmpty()) return;
        for (ChannelGroupObserver o : channelGroupObservers) {
            o.channelUpdated();
        }
    }

    @Override
    public String name() {
        return delegate.name();
    }

    @Override
    public Channel find(ChannelId id) {
        return delegate.find(id);
    }

    @Override
    public ChannelGroupFuture write(Object message) {
        return delegate.write(message);
    }

    @Override
    public ChannelGroupFuture write(Object message, ChannelMatcher matcher) {
        return delegate.write(message, matcher);
    }

    @Override
    public ChannelGroupFuture write(Object message, ChannelMatcher matcher, boolean voidPromise) {
        return delegate.write(message, matcher, voidPromise);
    }

    @Override
    public ChannelGroup flush() {
        return delegate.flush();
    }

    @Override
    public ChannelGroup flush(ChannelMatcher matcher) {
        return delegate.flush(matcher);
    }

    @Override
    public ChannelGroupFuture writeAndFlush(Object message) {
        return delegate.writeAndFlush(message);
    }

    @Override
    @Deprecated
    public ChannelGroupFuture flushAndWrite(Object message) {
        return delegate.flushAndWrite(message);
    }

    @Override
    public ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher) {
        return delegate.writeAndFlush(message, matcher);
    }

    @Override
    public ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher, boolean voidPromise) {
        return delegate.writeAndFlush(message, matcher, voidPromise);
    }

    @Override
    @Deprecated
    public ChannelGroupFuture flushAndWrite(Object message, ChannelMatcher matcher) {
        return delegate.flushAndWrite(message, matcher);
    }

    @Override
    public ChannelGroupFuture disconnect() {
        return delegate.disconnect();
    }

    @Override
    public ChannelGroupFuture disconnect(ChannelMatcher matcher) {
        return delegate.disconnect(matcher);
    }

    @Override
    public ChannelGroupFuture close() {
        return delegate.close();
    }

    @Override
    public ChannelGroupFuture close(ChannelMatcher matcher) {
        return delegate.close(matcher);
    }

    @Override
    @Deprecated
    public ChannelGroupFuture deregister() {
        return delegate.deregister();
    }

    @Override
    @Deprecated
    public ChannelGroupFuture deregister(ChannelMatcher matcher) {
        return delegate.deregister(matcher);
    }

    @Override
    public ChannelGroupFuture newCloseFuture() {
        return delegate.newCloseFuture();
    }

    @Override
    public ChannelGroupFuture newCloseFuture(ChannelMatcher matcher) {
        return delegate.newCloseFuture(matcher);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public Iterator<Channel> iterator() {
        return delegate.iterator();
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    @Override
    public boolean add(Channel channel) {
        if (delegate.add(channel)) {
            notifyChannelGroupChanges();
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (delegate.remove(o)) {
            notifyChannelGroupChanges();
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Channel> c) {
        if (delegate.addAll(c)) {
            notifyChannelGroupChanges();
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (delegate.retainAll(c)) {
            notifyChannelGroupChanges();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (delegate.removeAll(c)) {
            notifyChannelGroupChanges();
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        delegate.clear();
        notifyChannelGroupChanges();
    }

    @Override
    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public Spliterator<Channel> spliterator() {
        return delegate.spliterator();
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return delegate.toArray(generator);
    }

    @Override
    public boolean removeIf(Predicate<? super Channel> filter) {
        if (delegate.removeIf(filter)) {
            notifyChannelGroupChanges();
            return true;
        }
        return false;
    }

    @Override
    public Stream<Channel> stream() {
        return delegate.stream();
    }

    @Override
    public Stream<Channel> parallelStream() {
        return delegate.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super Channel> action) {
        delegate.forEach(action);
    }

    @Override
    public int compareTo(ChannelGroup o) {
        return delegate.compareTo(o);
    }
}

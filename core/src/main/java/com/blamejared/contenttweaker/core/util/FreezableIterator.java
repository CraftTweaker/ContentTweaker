package com.blamejared.contenttweaker.core.util;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public final class FreezableIterator<T> implements Iterator<T> {

    private final Iterator<T> delegate;
    private final AtomicBoolean freeze;
    private final boolean ownership;

    private FreezableIterator(final Iterator<T> delegate, final AtomicBoolean freeze, final boolean ownership) {
        this.delegate = delegate;
        this.freeze = freeze;
        this.ownership = ownership;
    }

    public static <T> FreezableIterator<T> of(final Iterator<T> delegate) {
        return of(delegate, false);
    }

    public static <T> FreezableIterator<T> of(final Iterator<T> delegate, final boolean freeze) {
        return new FreezableIterator<>(delegate, new AtomicBoolean(freeze), true);
    }

    static <T> FreezableIterator<T> of(final Iterator<T> delegate, final AtomicBoolean freeze) {
        return new FreezableIterator<>(delegate, freeze, false);
    }

    public void freeze() {
        if (!this.ownership) {
            throw new UnsupportedOperationException("freeze: Depends on another freezable object");
        }
        this.freeze.set(true);
    }

    @Override
    public boolean hasNext() {
        return this.delegate.hasNext();
    }

    @Override
    public T next() {
        return this.delegate.next();
    }

    @Override
    public void remove() {
        if (this.freeze.get()) {
            throw new UnsupportedOperationException("remove: Iterator is frozen");
        }
        this.delegate.remove();
    }

    @Override
    public void forEachRemaining(final Consumer<? super T> action) {
        this.delegate.forEachRemaining(action);
    }
}

package com.blamejared.contenttweaker.core.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public final class FreezableSpliterator<T> implements Spliterator<T> {

    private final Spliterator<T> delegate;
    private final AtomicBoolean frozen;
    private final boolean ownership;

    private FreezableSpliterator(final Spliterator<T> delegate, final AtomicBoolean frozen, final boolean ownership) {
        this.delegate = delegate;
        this.frozen = frozen;
        this.ownership = ownership;
    }

    public static <T> FreezableSpliterator<T> of(final Spliterator<T> delegate) {
        return of(delegate, false);
    }

    public static <T> FreezableSpliterator<T> of(final Spliterator<T> delegate, final boolean frozen) {
        return new FreezableSpliterator<>(delegate, new AtomicBoolean(frozen), true);
    }

    static <T> FreezableSpliterator<T> of(final Spliterator<T> delegate, final AtomicBoolean frozen) {
        return new FreezableSpliterator<>(delegate, frozen, false);
    }

    public void freeze() {
        if (!this.ownership) {
            throw new UnsupportedOperationException("freeze: Depends on another freezable object");
        }
        this.frozen.set(true);
    }

    @Override
    public boolean tryAdvance(final Consumer<? super T> action) {
        return this.delegate.tryAdvance(action);
    }

    @Override
    public Spliterator<T> trySplit() {
        return FreezableSpliterator.of(this.delegate.trySplit(), this.frozen);
    }

    @Override
    public long estimateSize() {
        return this.delegate.estimateSize();
    }

    @Override
    public int characteristics() {
        return this.delegate.characteristics();
    }

    @Override
    public void forEachRemaining(final Consumer<? super T> action) {
        this.delegate.forEachRemaining(action);
    }

    @Override
    public long getExactSizeIfKnown() {
        return this.delegate.getExactSizeIfKnown();
    }

    @Override
    public boolean hasCharacteristics(final int characteristics) {
        return this.delegate.hasCharacteristics(characteristics);
    }

    @Override
    public Comparator<? super T> getComparator() {
        return this.delegate.getComparator();
    }
}

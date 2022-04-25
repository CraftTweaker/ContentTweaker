package com.blamejared.contenttweaker.core.util;

import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

final class FreezableListIterator<T> implements ListIterator<T> {

    private final ListIterator<T> delegate;
    private final AtomicBoolean freeze;
    private final boolean ownership;

    private FreezableListIterator(final ListIterator<T> delegate, final AtomicBoolean freeze, final boolean ownership) {
        this.delegate = delegate;
        this.freeze = freeze;
        this.ownership = ownership;
    }

    public static <T> FreezableListIterator<T> of(final ListIterator<T> delegate) {
        return of(delegate, false);
    }

    public static <T> FreezableListIterator<T> of(final ListIterator<T> delegate, final boolean frozen) {
        return new FreezableListIterator<>(delegate, new AtomicBoolean(frozen), true);
    }

    static <T> FreezableListIterator<T> of(final ListIterator<T> delegate, final AtomicBoolean frozen) {
        return new FreezableListIterator<>(delegate, frozen, false);
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
    public boolean hasPrevious() {
        return this.delegate.hasPrevious();
    }

    @Override
    public T previous() {
        return this.delegate.previous();
    }

    @Override
    public int nextIndex() {
        return this.delegate.nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.delegate.previousIndex();
    }

    @Override
    public void remove() {
        if (this.freeze.get()) {
            throw new UnsupportedOperationException("remove: ListIterator is frozen");
        }
        this.delegate.remove();
    }

    @Override
    public void set(final T t) {
        if (this.freeze.get()) {
            throw new UnsupportedOperationException("set: ListIterator is frozen");
        }
        this.delegate.set(t);
    }

    @Override
    public void add(final T t) {
        if (this.freeze.get()) {
            throw new UnsupportedOperationException("add: ListIterator is frozen");
        }
        this.delegate.add(t);
    }

    @Override
    public void forEachRemaining(final Consumer<? super T> action) {
        this.delegate.forEachRemaining(action);
    }
}

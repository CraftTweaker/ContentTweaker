package com.blamejared.contenttweaker.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class FreezableCollection<T> implements Collection<T> {

    private final Collection<T> delegate;
    private final AtomicBoolean frozen;
    private final boolean ownership;

    private FreezableCollection(final Collection<T> delegate, final AtomicBoolean frozen, final boolean ownership) {
        this.delegate = delegate;
        this.frozen = frozen;
        this.ownership = ownership;
    }

    public static <T> FreezableCollection<T> of(final Collection<T> delegate) {
        return of(delegate, false);
    }

    public static <T> FreezableCollection<T> of(final Collection<T> delegate, final boolean frozen) {
        return new FreezableCollection<>(delegate, new AtomicBoolean(frozen), true);
    }

    static <T> FreezableCollection<T> of(final Collection<T> delegate, final AtomicBoolean frozen) {
        return new FreezableCollection<>(delegate, frozen, false);
    }

    public void freeze() {
        if (!this.ownership) {
            throw new UnsupportedOperationException("freeze: Depends on another freezable object");
        }
        this.frozen.set(true);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return this.delegate.contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return FreezableIterator.of(this.delegate.iterator(), this.frozen);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return this.delegate.toArray();
    }

    @NotNull
    @Override
    public <U> U[] toArray(@NotNull final U[] a) {
        return this.delegate.toArray(a);
    }

    @Override
    public boolean add(final T t) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("add: Collection is frozen");
        }
        return this.delegate.add(t);
    }

    @Override
    public boolean remove(final Object o) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("remove: Collection is frozen");
        }
        return this.delegate.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull final Collection<?> c) {
        return this.delegate.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull final Collection<? extends T> c) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("addAll: Collection is frozen");
        }
        return this.delegate.addAll(c);
    }

    @Override
    public boolean removeAll(@NotNull final Collection<?> c) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("removeAll: Collection is frozen");
        }
        return this.delegate.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull final Collection<?> c) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("retainAll: Collection is frozen");
        }
        return this.delegate.retainAll(c);
    }

    @Override
    public void clear() {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("clear: Collection is frozen");
        }
        this.delegate.clear();
    }

    @Override
    public <U> U[] toArray(final IntFunction<U[]> generator) {
        return this.delegate.toArray(generator);
    }

    @Override
    public boolean removeIf(final Predicate<? super T> filter) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("removeIf: Collection is frozen");
        }
        return this.delegate.removeIf(filter);
    }

    @Override
    public Spliterator<T> spliterator() {
        return FreezableSpliterator.of(this.delegate.spliterator(), this.frozen);
    }

    @Override
    public Stream<T> stream() {
        return this.delegate.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return this.delegate.parallelStream();
    }

    @Override
    public void forEach(final Consumer<? super T> action) {
        this.delegate.forEach(action);
    }
}

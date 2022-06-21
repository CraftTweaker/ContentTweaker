package com.blamejared.contenttweaker.core.util;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public final class FreezableList<T> implements List<T> {

    private final List<T> delegate;
    private final AtomicBoolean frozen;
    private final boolean ownership;

    private FreezableList(final List<T> delegate, final AtomicBoolean frozen, final boolean ownership) {
        this.delegate = delegate;
        this.frozen = frozen;
        this.ownership = ownership;
    }

    public static <T> FreezableList<T> of() {
        return of(new ArrayList<>());
    }

    public static <T> FreezableList<T> of(final List<T> delegate) {
        return of(delegate, false);
    }

    public static <T> FreezableList<T> of(final List<T> delegate, final boolean frozen) {
        return new FreezableList<>(delegate, new AtomicBoolean(frozen), true);
    }

    static <T> FreezableList<T> of(final List<T> delegate, final AtomicBoolean frozen) {
        return new FreezableList<>(delegate, frozen, false);
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
    public <T1> T1[] toArray(@NotNull final T1[] a) {
        return this.delegate.toArray(a);
    }

    @Override
    public boolean add(final T t) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("add: List is frozen");
        }
        return this.delegate.add(t);
    }

    @Override
    public boolean remove(final Object o) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("remove: List is frozen");
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
            throw new UnsupportedOperationException("addAll: List is frozen");
        }
        return this.delegate.addAll(c);
    }

    @Override
    public boolean addAll(final int index, @NotNull final Collection<? extends T> c) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("addAll: List is frozen");
        }
        return this.delegate.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NotNull final Collection<?> c) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("removeAll: List is frozen");
        }
        return this.delegate.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull final Collection<?> c) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("retainAll: List is frozen");
        }
        return this.delegate.retainAll(c);
    }

    @Override
    public void clear() {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("clear: List is frozen");
        }
        this.delegate.clear();
    }

    @Override
    public T get(final int index) {
        return this.delegate.get(index);
    }

    @Override
    public T set(final int index, final T element) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("set: List is frozen");
        }
        return this.delegate.set(index, element);
    }

    @Override
    public void add(final int index, final T element) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("add: List is frozen");
        }
        this.delegate.add(index, element);
    }

    @Override
    public T remove(final int index) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("remove: List is frozen");
        }
        return this.delegate.remove(index);
    }

    @Override
    public int indexOf(final Object o) {
        return this.delegate.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return this.delegate.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return FreezableListIterator.of(this.delegate.listIterator(), this.frozen);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(final int index) {
        return FreezableListIterator.of(this.delegate.listIterator(index), this.frozen);
    }

    @NotNull
    @Override
    public List<T> subList(final int fromIndex, final int toIndex) {
        return FreezableList.of(this.delegate.subList(fromIndex, toIndex), this.frozen);
    }

    @Override
    public void replaceAll(final UnaryOperator<T> operator) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("replaceAll: List is frozen");
        }
        this.delegate.replaceAll(operator);
    }

    @Override
    public void sort(final Comparator<? super T> c) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("sort: List is frozen");
        }
        this.delegate.sort(c);
    }

    @Override
    public Spliterator<T> spliterator() {
        return FreezableSpliterator.of(this.delegate.spliterator(), this.frozen);
    }

    @Override
    public <U> U[] toArray(final IntFunction<U[]> generator) {
        return this.delegate.toArray(generator);
    }

    @Override
    public boolean removeIf(final Predicate<? super T> filter) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("removeIf: List is frozen");
        }
        return this.delegate.removeIf(filter);
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

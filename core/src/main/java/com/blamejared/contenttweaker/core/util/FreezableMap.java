package com.blamejared.contenttweaker.core.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class FreezableMap<K, V> implements Map<K, V> {

    private final Map<K, V> delegate;
    private final AtomicBoolean frozen;
    private final boolean ownership;

    private FreezableMap(final Map<K, V> delegate, final AtomicBoolean frozen, final boolean ownership) {
        this.delegate = delegate;
        this.frozen = frozen;
        this.ownership = ownership;
    }

    public static <K, V> FreezableMap<K, V> of() {
        return of(new HashMap<>());
    }

    public static <K, V> FreezableMap<K, V> of(final Map<K, V> delegate) {
        return of(delegate, false);
    }

    public static <K, V> FreezableMap<K, V> of(final Map<K, V> delegate, final boolean frozen) {
        return new FreezableMap<>(delegate, new AtomicBoolean(frozen), true);
    }

    static <K, V> FreezableMap<K, V> of(final Map<K, V> delegate, final AtomicBoolean frozen) {
        return new FreezableMap<>(delegate, frozen, false);
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
    public boolean containsKey(final Object key) {
        return this.delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return this.delegate.containsValue(value);
    }

    @Override
    public V get(final Object key) {
        return this.delegate.get(key);
    }

    @Nullable
    @Override
    public V put(final K key, final V value) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("put: Map is frozen");
        }
        return this.delegate.put(key, value);
    }

    @Override
    public V remove(final Object key) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("remove: Map is frozen");
        }
        return this.delegate.remove(key);
    }

    @Override
    public void putAll(@NotNull final Map<? extends K, ? extends V> m) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("putAll: Map is frozen");
        }
        this.delegate.putAll(m);
    }

    @Override
    public void clear() {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("clear: Map is frozen");
        }
        this.delegate.clear();
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return FreezableSet.of(this.delegate.keySet(), this.frozen);
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return FreezableCollection.of(this.delegate.values(), this.frozen);
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return FreezableSet.of(this.delegate.entrySet(), this.frozen);
    }

    @Override
    public V getOrDefault(final Object key, final V defaultValue) {
        return this.delegate.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(final BiConsumer<? super K, ? super V> action) {
        this.delegate.forEach(action);
    }

    @Override
    public void replaceAll(final BiFunction<? super K, ? super V, ? extends V> function) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("replaceAll: Map is frozen");
        }
        this.delegate.replaceAll(function);
    }

    @Nullable
    @Override
    public V putIfAbsent(final K key, final V value) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("putIfAbsent: Map is frozen");
        }
        return this.delegate.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(final Object key, final Object value) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("remove: Map is frozen");
        }
        return this.delegate.remove(key, value);
    }

    @Override
    public boolean replace(final K key, final V oldValue, final V newValue) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("replace: Map is frozen");
        }
        return this.delegate.replace(key, oldValue, newValue);
    }

    @Nullable
    @Override
    public V replace(final K key, final V value) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("replace: Map is frozen");
        }
        return this.delegate.replace(key, value);
    }

    @Override
    public V computeIfAbsent(final K key, @NotNull final Function<? super K, ? extends V> mappingFunction) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("computeIfAbsent: Map is frozen");
        }
        return this.delegate.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(final K key, @NotNull final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("computeIfPresent: Map is frozen");
        }
        return this.delegate.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(final K key, @NotNull final BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("compute: Map is frozen");
        }
        return this.delegate.compute(key, remappingFunction);
    }

    @Override
    public V merge(final K key, @NotNull final V value, @NotNull final BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (this.frozen.get()) {
            throw new UnsupportedOperationException("merge: Map is frozen");
        }
        return this.delegate.merge(key, value, remappingFunction);
    }
}

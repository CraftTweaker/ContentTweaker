package com.blamejared.contenttweaker.core.api.util;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ClassArchitect<T> {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();

    private final Map<Class<? extends T>, MethodHandle> constructors;
    private final MethodType constructorTarget;

    private ClassArchitect(final MethodType constructorTarget) {
        this.constructors = new HashMap<>();
        this.constructorTarget = constructorTarget;
    }

    public static <T> ClassArchitect<T> of(final MethodType constructorTarget) {
        Objects.requireNonNull(constructorTarget);
        return new ClassArchitect<>(constructorTarget);
    }

    public static <T> ClassArchitect<T> of(final Class<?>... constructorParameters) {
        return of(MethodType.methodType(void.class, constructorParameters));
    }

    public <U extends T> U construct(final Class<U> clazz) {
        return this.construct(clazz, (Object[]) null);
    }

    public <U extends T> U construct(final Class<U> clazz, final Object... constructorParameters) {
        try {
            Objects.requireNonNull(clazz);
            final MethodHandle target = this.constructors.computeIfAbsent(clazz, this::findConstructor);
            return GenericUtil.uncheck(target.invokeWithArguments(constructorParameters));
        } catch (final WrongMethodTypeException | ClassCastException e) {
            CraftTweakerAPI.LOGGER.error(() -> "Unable to construct class '" + clazz.getName() + "' due to an invocation error", e);
            throw e;
        } catch (final Throwable e) {
            CraftTweakerAPI.LOGGER.error(() -> "Unable to construct class '" + clazz.getName() + "' due to a construction error", e);
            if (e instanceof RuntimeException re) throw re;
            throw new RuntimeException("%s: %s".formatted(e.getClass().getName(), e.getMessage()), e);
        }
    }

    private <U extends T> MethodHandle findConstructor(final Class<U> clazz) {
        try {
            return LOOKUP.findConstructor(clazz, this.constructorTarget);
        } catch (final NoSuchMethodException | IllegalAccessException | SecurityException e) {
            CraftTweakerAPI.LOGGER.error(() -> "Unable to identify constructor for class '" + clazz.getName() + "' due to a reflective error", e);
            throw new RuntimeException(e);
        }
    }
}

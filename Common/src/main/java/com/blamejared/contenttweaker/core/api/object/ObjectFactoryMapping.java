package com.blamejared.contenttweaker.core.api.object;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;

@FunctionalInterface
public interface ObjectFactoryMapping<T, U extends ObjectFactory<T>> {
    Class<U> type();

    default U of() {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.publicLookup();
            final MethodType constructorType = MethodType.methodType(void.class);
            final MethodHandle constructor = lookup.findConstructor(this.type(), constructorType);
            return GenericUtil.uncheck(constructor.invoke());
        } catch (final WrongMethodTypeException | ClassCastException e) {
            CraftTweakerAPI.LOGGER.error(() -> "Unable to construct class '" + this.type().getName() + "' due to an invocation error", e);
            throw e;
        } catch (final Throwable e) {
            CraftTweakerAPI.LOGGER.error(() -> "Unable to construct class '" + this.type().getName() + "' due to a construction error", e);
            if (e instanceof RuntimeException re) throw re;
            throw new RuntimeException("%s: %s".formatted(e.getClass().getName(), e.getMessage()), e);
        }
    }
}

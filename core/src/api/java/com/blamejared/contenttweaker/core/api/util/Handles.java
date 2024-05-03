package com.blamejared.contenttweaker.core.api.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.util.Objects;

public interface Handles {
    interface Provider {
        Handles publicOnlyHandles();
        Handles fullTrustHandles();
        <T> T handleInvocation(final Invoker<T> invoker);
    }

    interface Names {
        static Names of(final String shared) {
            Objects.requireNonNull(shared);
            return of(shared, shared, shared);
        }

        static Names of(final String intermediary, final String srg, final String named) {
            Objects.requireNonNull(intermediary);
            Objects.requireNonNull(srg);
            Objects.requireNonNull(named);
            return new Names() {
                @Override
                public String intermediary() {
                    return intermediary;
                }

                @Override
                public String srg() {
                    return srg;
                }

                @Override
                public String named() {
                    return named;
                }
            };
        }

        String intermediary();
        String srg();
        String named();
    }

    @FunctionalInterface
    interface Invoker<T> {
        T invoke() throws Throwable;
    }

    @FunctionalInterface
    interface VoidInvoker extends Invoker<Void> {
        void invokeVoid() throws Throwable;

        @Override
        default Void invoke() throws Throwable {
            this.invokeVoid();
            return null;
        }
    }

    final class HandleLinkageFailure extends RuntimeException {
        public HandleLinkageFailure(final Throwable cause) {
            super(cause);
        }
    }

    static Handles trusted() {
        return ContentTweakerApi.get().handles().fullTrustHandles();
    }

    static Handles publicOnly() {
        return ContentTweakerApi.get().handles().publicOnlyHandles();
    }

    static <T> T invoke(final Invoker<T> invoker) {
        return ContentTweakerApi.get().handles().handleInvocation(invoker);
    }

    static void invokeVoid(final VoidInvoker invoker) {
        invoke(invoker);
    }

    MethodHandle linkMethod(final HandleAccess access, final Class<?> owner, final Names names, final MethodType methodType);

    default MethodHandle linkMethod(final HandleAccess access, final Class<?> owner, final String name, final MethodType methodType) {
        return this.linkMethod(access, owner, Names.of(name), methodType);
    }

    default MethodHandle linkMethod(final HandleAccess access, final Class<?> owner, final Names names, final Class<?> returnType, final Class<?>... parameters) {
        return this.linkMethod(access, owner, names, MethodType.methodType(returnType, parameters));
    }

    default MethodHandle linkMethod(final HandleAccess access, final Class<?> owner, final String name, final Class<?> returnType, final Class<?>... parameters) {
        return this.linkMethod(access, owner, Names.of(name), returnType, parameters);
    }

    default MethodHandle linkConstructor(final Class<?> owner, final MethodType methodType) {
        return this.linkMethod(HandleAccess.constructorAccess(), owner, "<init>", methodType);
    }

    default MethodHandle linkConstructor(final Class<?> owner, final Class<?>... parameters) {
        return this.linkConstructor(owner, MethodType.methodType(void.class, parameters));
    }

    VarHandle linkField(final HandleAccess access, final Class<?> owner, final Names names, final Class<?> type);

    default VarHandle linkField(final HandleAccess access, final Class<?> owner, final String name, final Class<?> type) {
        return this.linkField(access, owner, Names.of(name), type);
    }
}

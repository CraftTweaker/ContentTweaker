package com.blamejared.contenttweaker.core.util;

import com.blamejared.contenttweaker.core.api.util.HandleAccess;
import com.blamejared.contenttweaker.core.api.util.Handles;
import com.blamejared.contenttweaker.core.service.ServiceManager;
import com.dwarveddonuts.neverwinter.handle.AccessType;
import com.dwarveddonuts.neverwinter.handle.RequestType;
import com.dwarveddonuts.neverwinter.handle.UnableToLinkHandleException;
import com.dwarveddonuts.neverwinter.handle.UnableToObtainLookupException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

public final class NeverWinterBackedHandles implements Handles.Provider {
    private record RequestBoundHandles(RequestType requestType) implements Handles {
        @Override
        public MethodHandle linkMethod(final HandleAccess access, final Class<?> owner, final Names names, final MethodType methodType) {
            try {
                return com.dwarveddonuts.neverwinter.handle.Handles.linkMethod(this.requestType(), this.neverWinterAccess(access), owner, this.pickName(names), methodType);
            } catch (final UnableToLinkHandleException | UnableToObtainLookupException e) {
                throw new HandleLinkageFailure(e);
            }
        }

        @Override
        public VarHandle linkField(final HandleAccess access, final Class<?> owner, final Names names, final Class<?> type) {
            try {
                return com.dwarveddonuts.neverwinter.handle.Handles.linkField(this.requestType(), this.neverWinterAccess(access), owner, this.pickName(names), type);
            } catch (final UnableToLinkHandleException | UnableToObtainLookupException e) {
                throw new HandleLinkageFailure(e);
            }
        }

        private AccessType neverWinterAccess(final HandleAccess access) {
            return switch (access.type()) {
                case STATIC -> AccessType.staticAccess();
                case VIRTUAL -> AccessType.virtualAccess();
                case DIRECT -> AccessType.directAccess();
                case SPECIAL -> AccessType.specialAccess(access.parameter());
                case CONSTRUCTOR -> AccessType.constructorAccess();
            };
        }

        private String pickName(final Names names) {
            return ServiceManager.platform().pickNameFromChoices(names.intermediary(), names.srg(), names.named());
        }
    }

    private final RequestBoundHandles fullTrust;
    private final RequestBoundHandles publicOnly;

    private NeverWinterBackedHandles() {
        this.fullTrust = new RequestBoundHandles(RequestType.trusted());
        this.publicOnly = new RequestBoundHandles(RequestType.publicOnly());
    }

    public static NeverWinterBackedHandles of() {
        return new NeverWinterBackedHandles();
    }

    @Override
    public Handles publicOnlyHandles() {
        return this.publicOnly;
    }

    @Override
    public Handles fullTrustHandles() {
        return this.fullTrust;
    }

    @Override
    public <T> T handleInvocation(final Handles.Invoker<T> invoker) {
        return com.dwarveddonuts.neverwinter.handle.Handles.invokeHandle(invoker::invoke);
    }
}

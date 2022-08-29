package com.blamejared.contenttweaker.core.resource.trundle;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.Util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TrundleFileSystemProviderInjector {
    private TrundleFileSystemProviderInjector() {}

    public static void inject() {
        if (exists()) return;

        try {
            final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            final MethodHandles.Lookup unsafeLookup = MethodHandles.privateLookupIn(unsafeClass, MethodHandles.lookup());
            final VarHandle unsafeField = unsafeLookup.findStaticVarHandle(unsafeClass, "theUnsafe", unsafeClass);
            final MethodHandle staticFieldBase = unsafeLookup.findVirtual(unsafeClass, "staticFieldBase", MethodType.methodType(Object.class, Field.class));
            final MethodHandle staticFieldOffset = unsafeLookup.findVirtual(unsafeClass, "staticFieldOffset", MethodType.methodType(long.class, Field.class));
            final MethodHandle getObject = unsafeLookup.findVirtual(unsafeClass, "getObject", MethodType.methodType(Object.class, Object.class, long.class));
            final MethodHandle putObject = unsafeLookup.findVirtual(unsafeClass, "putObject", MethodType.methodType(void.class, Object.class, long.class, Object.class));

            final Field providersField = FileSystemProvider.class.getDeclaredField("installedProviders");
            final Field lockField = FileSystemProvider.class.getDeclaredField("lock");

            final Object unsafe = unsafeField.get();

            final Object providersBase = staticFieldBase.invoke(unsafe, providersField);
            final long providersOffset = (long) staticFieldOffset.invoke(unsafe, providersField);
            final Object lockBase = staticFieldBase.invoke(unsafe, lockField);
            final long lockOffset = (long) staticFieldOffset.invoke(unsafe, lockField);

            final Object lock = getObject.invoke(unsafe, lockBase, lockOffset);

            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (lock) {
                final Object providersObject = getObject.invoke(unsafe, providersBase, providersOffset);
                final List<FileSystemProvider> providers = GenericUtil.uncheck(providersObject);

                final List<FileSystemProvider> newProviders = Collections.unmodifiableList(Util.make(new ArrayList<>(providers), it -> it.add(new TrundleFileSystemProvider())));
                putObject.invoke(unsafe, providersBase, providersOffset, newProviders);
            }

            if (!exists()) {
                throw new RuntimeException("Unable to inject file system provider: discovered " + FileSystemProvider.installedProviders());
            } else {
                ContentTweakerCore.LOGGER.info("Successfully injected Trundle file system");
            }
        } catch (final Throwable e) {
            throw new RuntimeException("An error occurred when attempting to inject Trundle file system provider", e);
        }
    }

    private static boolean exists() {
        return FileSystemProvider.installedProviders().stream().anyMatch(it -> TrundleFileSystemProvider.SCHEME.equals(it.getScheme()));
    }
}

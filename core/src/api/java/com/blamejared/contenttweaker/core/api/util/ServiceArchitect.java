package com.blamejared.contenttweaker.core.api.util;

import com.google.common.base.Suppliers;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public final class ServiceArchitect {
    private static final Supplier<String> OUR_OWN_PACKAGE = Suppliers.memoize(() -> {
        final String thisPackage = ServiceArchitect.class.getPackageName();
        // This class is nested within three packages of the project module name
        final String apiPackage = thisPackage.substring(0, thisPackage.lastIndexOf('.'));
        final String modulePackage = apiPackage.substring(0, apiPackage.lastIndexOf('.'));
        final String projectPackage = modulePackage.substring(0, modulePackage.lastIndexOf('.'));
        return projectPackage.intern();
    });

    private ServiceArchitect() {}

    public static <S> Supplier<S> find(final Class<S> serviceClass) {
        return find(serviceClass, () -> "Unable to find an implementation for the target service " + serviceClass.getName());
    }

    public static <S> Supplier<S> find(final Class<S> serviceClass, final Supplier<String> messageProvider) {
        return Suppliers.memoize(() -> findWithPreference(serviceClass, messageProvider));
    }

    private static <S> S findWithPreference(final Class<S> serviceClass, final Supplier<String> messageProvider) {
        return ServiceLoader.load(serviceClass)
                .stream()
                .min(ServiceArchitect::compareProviders)
                .map(ServiceLoader.Provider::get)
                .orElseThrow(() -> new IllegalStateException(messageProvider.get()));
    }

    private static <S> int compareProviders(final ServiceLoader.Provider<S> a, final ServiceLoader.Provider<S> b) {
        return compareClassesWithPreference(a.type(), b.type());
    }

    private static <S> int compareClassesWithPreference(final Class<? extends S> a, final Class<? extends S> b) {
        if (a == b) {
            return 0;
        }

        final var aPackage = a.getPackageName();
        final var bPackage = b.getPackageName();

        final var aIsOwn = isOwnPackage(aPackage);
        final var bIsOwn = isOwnPackage(bPackage);

        if (aIsOwn && bIsOwn) {
            return 0;
        }
        if (aIsOwn) {
            return -1;
        }
        if (bIsOwn) {
            return 1;
        }

        return 0;
    }

    private static boolean isOwnPackage(final String packageName) {
        return packageName.startsWith(OUR_OWN_PACKAGE.get());
    }
}

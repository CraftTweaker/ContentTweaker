package com.blamejared.contenttweaker.core.util;

import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.datafixers.util.Either;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class NonApiCraftTweakerWrapper {
    private NonApiCraftTweakerWrapper() {}

    public static <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(final Class<T> annotationCls) {
        return Services.PLATFORM.findClassesWithAnnotation(annotationCls);
    }

    public static <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
            final Class<T> annotationCls,
            final Consumer<Mod> consumer
    ) {
        return Services.PLATFORM.findClassesWithAnnotation(annotationCls, consumer);
    }

    public static <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
            final Class<T> annotationCls,
            final Consumer<Mod> consumer,
            final Predicate<Either<T, Map<String, Object>>> annotationFilter
    ) {
        return Services.PLATFORM.findClassesWithAnnotation(annotationCls, consumer, annotationFilter);
    }
}

package com.teamacronymcoders.contenttweaker.api.utils;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ResourceList<RESOURCE> {
    private Class<RESOURCE> resourceClass;
    private List<Class> classesToCheck;

    public ResourceList(@Nonnull Class<RESOURCE> resourceClass) {
        this(resourceClass, resourceClass);
    }

    public ResourceList(@Nonnull Class<RESOURCE> resourceClass, @Nonnull Class classToCheck) {
        this(resourceClass, Lists.newArrayList(classToCheck));
    }

    public ResourceList(@Nonnull Class<RESOURCE> resourceClass, @Nonnull List<Class> classesToCheck) {
        this.resourceClass = resourceClass;
        this.classesToCheck = classesToCheck;
    }

    public void addClassToCheck(Class classToCheck) {
        this.classesToCheck.add(classToCheck);
    }

    private Map<String, RESOURCE> resources = new HashMap<>();

    public void addResource(String name, RESOURCE resource) {
        resources.put(name.toLowerCase(Locale.US), resource);
    }

    public RESOURCE getResource(String name) {
        return resources.get(name.toLowerCase(Locale.US));
    }

    private void loadResources() {
        this.classesToCheck.forEach(clazz -> {
            Reflection.getStaticFieldsOfType(this.resourceClass, clazz).forEach((name, resource) -> {
                resources.put(name, resource);
            });
        });
    }
}

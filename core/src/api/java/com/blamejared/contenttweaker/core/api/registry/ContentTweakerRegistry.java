package com.blamejared.contenttweaker.core.api.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import net.minecraft.resources.ResourceLocation;

public interface ContentTweakerRegistry {
    <T> ObjectType<T> findType(final ResourceLocation id, final Class<T> type);
    <T, U extends ObjectFactory<T>> ObjectFactoryMapping<T, U> findObjectFactory(final ObjectType<T> type, final Class<U> factoryType);
    <T, U extends Reference<T>> ReferenceFactory<T, U> findReferenceFactory(final ObjectType<T> type, final Class<U> referenceType);
    <T> RegistryResolver<T> findResolver(final ObjectType<T> type);
}

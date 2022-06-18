package com.blamejared.contenttweaker.core.zen.rt;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.registry.MetaRegistry;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(FactoryMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class FactoryMetaFactory {
    public static final String ZEN_NAME = ContentTweakerZenConstants.RT_PACKAGE + ".FactoryMetaFactory";

    private FactoryMetaFactory() {}

    @SuppressWarnings("unused") // Reified types need to exist, but we do not use them
    @ZenCodeType.Method("factory")
    public static <T, U extends ObjectFactory<T>> U factory(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation registryId) {
        final MetaRegistry metaRegistry = ContentTweakerCore.core().metaRegistry();
        final ResourceKey<? extends Registry<T>> key = ResourceKey.createRegistryKey(registryId);
        final ObjectType<T> type = metaRegistry.objectTypes().get(key);
        final ObjectFactoryMapping<T, U> factoryClass = metaRegistry.factoryMappings().findMappingFor(type);
        return factoryClass.of();
    }
}

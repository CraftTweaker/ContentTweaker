package com.blamejared.contenttweaker.core.zen.rt;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.registry.MetaRegistry;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerZenConstants.CORE_META_FACTORY_ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CoreMetaFactory {
    private CoreMetaFactory() {}

    @SuppressWarnings("unused") // Reified types need to exist, but we do not use them
    @ZenCodeType.Method("factory")
    public static <T, U extends ObjectFactory<T>> U factory(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation typeId) {
        final MetaRegistry metaRegistry = ContentTweakerCore.core().metaRegistry();
        final ObjectType<T> type = metaRegistry.objectTypes().get(typeId);
        final ObjectFactoryMapping<T, U> factoryClass = metaRegistry.factoryMappings().findMappingFor(type);
        return factoryClass.of();
    }

    @SuppressWarnings("unused") // Reified types
    @ZenCodeType.Method("reference")
    public static <T, U extends Reference<T>> U reference(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation typeId, final ResourceLocation id) {
        final MetaRegistry metaRegistry = ContentTweakerCore.core().metaRegistry();
        final ObjectType<T> type = metaRegistry.objectTypes().get(typeId);
        final ReferenceFactory<T, U> factory = metaRegistry.referenceFactories().findFactoryFor(type);
        return factory.of(type, id);
    }
}

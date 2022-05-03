package com.blamejared.contenttweaker.core.zen.rt;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.registry.MetaRegistry;
import com.blamejared.contenttweaker.core.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ReferenceMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ReferenceMetaFactory {
    public static final String ZEN_NAME = ContentTweakerZenConstants.RT_PACKAGE + ".ReferenceMetaFactory";

    private ReferenceMetaFactory() {}

    @SuppressWarnings("unused") // Reified types
    @ZenCodeType.Method("of")
    public static <T, U extends Reference<T>> U of(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation registryId, final ResourceLocation id) {
        final MetaRegistry metaRegistry = ContentTweakerCore.core().metaRegistry();
        final ResourceKey<? extends Registry<T>> key = ResourceKey.createRegistryKey(registryId);
        final ObjectType<T> type = metaRegistry.objectTypes().get(key);
        return (U) new Reference<>(type, id) {}; // TODO("Work in progress: probably a system similar to object factories")
    }
}

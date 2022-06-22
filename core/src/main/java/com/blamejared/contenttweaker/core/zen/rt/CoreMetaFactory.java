package com.blamejared.contenttweaker.core.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.registry.ContentTweakerRegistry;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerZenConstants.CORE_META_FACTORY_ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CoreMetaFactory {
    private CoreMetaFactory() {}

    @ZenCodeType.Method("factory")
    public static <T, U extends ObjectFactory<T>> U factory(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation typeId) {
        final ContentTweakerRegistry registry = ContentTweakerApi.get().registry();
        final ObjectType<T> type = registry.findType(typeId, reifiedT);
        final ObjectFactoryMapping<T, U> factoryClass = registry.findObjectFactory(type, reifiedU);
        return factoryClass.of();
    }

    @ZenCodeType.Method("reference")
    public static <T, U extends Reference<T>> U reference(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation typeId, final ResourceLocation id) {
        final ContentTweakerRegistry registry = ContentTweakerApi.get().registry();
        final ObjectType<T> type = registry.findType(typeId, reifiedT);
        final ReferenceFactory<T, U> factory = registry.findReferenceFactory(type, reifiedU);
        return factory.of(type, id);
    }
}

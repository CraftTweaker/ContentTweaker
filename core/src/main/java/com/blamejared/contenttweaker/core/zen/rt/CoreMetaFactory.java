package com.blamejared.contenttweaker.core.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.registry.ContentTweakerRegistry;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * <p> A class to supply factorys and references dynamically </p>
 */
@ZenCodeType.Name(ContentTweakerZenConstants.CORE_META_FACTORY_ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
@Document("mods/ContentTweaker/internal/CoreMetaFactory")
public final class CoreMetaFactory {
    private CoreMetaFactory() {}

    /**
     * Create a factory dynamically
     *
     * @param reifiedT The class of objects to create
     * @param reifiedU The class of the factory
     * @param typeId The type id of the object type to create
     * @return A new factory
     *
     * @param <T> A Builder Type, such as contenttweaker.builder.vanilla.item.Simple
     * @param <U> A Factory Type, such as contenttweaker.factory.vanilla.ItemFactory
     *
     */
    /*
     * @docParam <T> &lt;Simple&gt;
     * @docParam <U> &lt;ItemFactory&gt;
     * @docParam typeId ResourceLocation.of("minecraft:item")
     */
    @ZenCodeType.Method("factory")
    public static <T, U extends ObjectFactory<T>> U factory(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation typeId) {
        final ContentTweakerRegistry registry = ContentTweakerApi.get().registry();
        final ObjectType<T> type = registry.findType(typeId, reifiedT);
        return registry.findObjectFactory(type, reifiedU);
    }

    /**
     * Create a reference dynamically
     *
     * @param reifiedT The class of objects to reference
     * @param reifiedU The class of the reference
     * @param typeId The type id of the object type to reference
     * @param id The id of the reference
     * @return A new reference
     *
     * @param <T> A Builder Type, such as contenttweaker.builder.vanilla.item.Simple
     * @param <U> A Reference Type, such as contenttweaker.object.SimpleReference
     *
     */
    @ZenCodeType.Method("reference")
    public static <T, U extends Reference<T>> U reference(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation typeId, final ResourceLocation id) {
        final ContentTweakerRegistry registry = ContentTweakerApi.get().registry();
        final ObjectType<T> type = registry.findType(typeId, reifiedT);
        final ReferenceFactory<T, U> factory = registry.findReferenceFactory(type, reifiedU);
        return factory.of(type, id);
    }
}

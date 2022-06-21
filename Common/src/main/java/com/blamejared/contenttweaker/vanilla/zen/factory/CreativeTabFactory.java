package com.blamejared.contenttweaker.vanilla.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.registry.CreativeTabRegistry;
import com.blamejared.contenttweaker.vanilla.api.resource.Language;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.util.CustomCreativeTab;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".CreativeTabFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CreativeTabFactory implements ObjectFactory<CreativeModeTab> {
    public CreativeTabFactory() {}

    @Override
    public ObjectType<CreativeModeTab> type() {
        return VanillaObjectTypes.CREATIVE_TAB;
    }

    @ZenCodeType.Method("create")
    public CreativeTabReference create(final String id, final ItemReference icon) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(icon);
        final ResourceLocation registryId = CreativeTabRegistry.fromId(id);
        final ObjectHolder<CreativeModeTab> holder = ObjectHolder.of(this.type(), registryId, () -> CustomCreativeTab.of(id, icon));
        ContentTweakerApi.apply(RegisterObjectAction.of(holder, resource -> this.provideResources(resource, id)));
        return CreativeTabReference.of(id);
    }

    private void provideResources(final ResourceManager manager, final String id) {
        final ResourceFragment assets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        assets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.tab(id, "Custom Tab"), Language.SERIALIZER);
    }
}

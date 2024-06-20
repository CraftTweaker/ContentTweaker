package com.blamejared.contenttweaker.fabric.api.zen.builder.tab;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.fabric.api.zen.ContentTweakerFabricConstants;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.tab.CreativeTabBuilder;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenCodeType.Name(ContentTweakerFabricConstants.TAB_BUILDER_PACKAGE + ".Regular")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class RegularCreativeTabBuilder extends CreativeTabBuilder<RegularCreativeTabBuilder> {
    public RegularCreativeTabBuilder(final BiFunction<ObjectHolder<? extends CreativeModeTab>, Consumer<ResourceManager>, CreativeTabReference> registrationHandler) {
        super(registrationHandler);
    }

    @Override
    protected ObjectHolder<? extends CreativeModeTab> create(final ResourceLocation name, final GenerateFlags flags) {
        if (!flags.placeAutomatically()) {
            throw new IllegalStateException("Manual placement of creative tabs is not supported");
        }
        return ObjectHolder.of(VanillaObjectTypes.CREATIVE_TAB, name, () -> this.fillBuilder(FabricItemGroup.builder()).build());
    }

    @Override
    protected void provideResources(final ResourceLocation name, final GenerateFlags flags, final ResourceManager manager) {}
}

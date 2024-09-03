package com.blamejared.contenttweaker.forge.api.zen.builder.tab;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.forge.api.zen.ContentTweakerForgeConstants;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.tab.CreativeTabBuilder;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * <p>The Forge {@link CreativeTabBuilder} implementation.</p>
 *
 * Must be imported and used in creative mode tab factorys.
 *
 * <pre>
 * <code class=language-zenscript>import contenttweaker.builder.fabric.tab.Regular;
 *
 *val emptyTabsCrash = &lt;factory:minecraft:creative_mode_tab&gt;
 *    .typed&lt;Regular&gt;() //Must be imported
 *    .title("The First Apple")
 *    .icon(&lt;item:minecraft:air&gt;)
 *    .display(&lt;item:minecraft:apple&gt;)
 *    .build("empty_tab");</code></pre>
 *
 */
@Document("mods/ContentTweaker/forge/builder/tab/RegularCreativeTabBuilder")
@ZenCodeType.Name(ContentTweakerForgeConstants.TAB_BUILDER_PACKAGE + ".Regular")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class RegularCreativeTabBuilder extends CreativeTabBuilder<RegularCreativeTabBuilder> {
    public RegularCreativeTabBuilder(final BiFunction<ObjectHolder<? extends CreativeModeTab>, Consumer<ResourceManager>, CreativeTabReference> registrationHandler) {
        super(registrationHandler);
    }

    @Override
    protected ObjectHolder<? extends CreativeModeTab> create(final ResourceLocation name, final GenerateFlags flags) {
        return ObjectHolder.of(VanillaObjectTypes.CREATIVE_TAB, name, () -> this.fillBuilder(this.makeBuilder(flags)).build());
    }

    @Override
    protected void provideResources(final ResourceLocation name, final GenerateFlags flags, final ResourceManager manager) {}

    @SuppressWarnings("deprecation") // Explicit positioning is deprecated
    private CreativeModeTab.Builder makeBuilder(final GenerateFlags flags) {
        if (flags.placeAutomatically()) {
            return CreativeModeTab.builder();
        }

        final GenerateFlags.Position position = flags.position();
        return CreativeModeTab.builder(position.row(), position.column());
    }
}

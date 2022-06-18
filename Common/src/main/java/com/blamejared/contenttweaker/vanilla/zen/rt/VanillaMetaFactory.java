package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.util.Color;
import com.blamejared.contenttweaker.vanilla.api.action.tab.CreateCreativeTabAction;
import com.blamejared.contenttweaker.vanilla.api.util.MaterialRegistry;
import com.blamejared.contenttweaker.vanilla.api.util.SoundTypeRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.util.CreativeTabReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.MaterialColorReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.MaterialReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.SoundTypeReference;
import com.blamejared.contenttweaker.vanilla.mixin.CreativeModeTabAccessor;
import com.blamejared.contenttweaker.vanilla.util.CustomCreativeTab;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Objects;

@ZenCodeType.Name(VanillaMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class VanillaMetaFactory {
    public static final String ZEN_NAME = ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".MetaFactory";

    @ZenCodeType.Method("materialColor")
    public static MaterialColorReference materialColor(final int id, final int color) {
        return MaterialColorReference.of(id, Color.packedRgb(color), MaterialColor::byId);
    }

    @ZenCodeType.Method("material")
    public static MaterialReference material(final ResourceLocation name) {
        return MaterialReference.of(Objects.requireNonNull(name), id -> MaterialRegistry.of().find(id));
    }

    @ZenCodeType.Method("soundType")
    public static SoundTypeReference soundType(final ResourceLocation name) {
        return SoundTypeReference.of(Objects.requireNonNull(name), () -> SoundTypeRegistry.of().find(name));
    }

    @ZenCodeType.Method("tab")
    public static CreativeTabReference tab(final String name) {
        Objects.requireNonNull(name);
        return CreativeTabReference.of(
                name,
                () -> Arrays.stream(CreativeModeTab.TABS)
                        .filter(it -> name.equals(((CreativeModeTabAccessor) it).contenttweaker$langId()))
                        .findFirst()
                        .orElseGet(() -> {
                            final CreativeModeTab[] tab = new CreativeModeTab[1];
                            final CreateCreativeTabAction action = CreateCreativeTabAction.of(name, () -> tab[0] = CustomCreativeTab.of(name));
                            ContentTweakerApi.apply(action);
                            return tab[0];
                        })
        );
    }
}

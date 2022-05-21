package com.blamejared.contenttweaker.vanilla.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.action.tab.CreateCreativeTabAction;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.util.CreativeTab;
import com.blamejared.contenttweaker.vanilla.mixin.CreativeModeTabAccessor;
import com.blamejared.contenttweaker.vanilla.util.CustomCreativeTab;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Objects;

@ZenCodeType.Name(TabMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TabMetaFactory {
    public static final String ZEN_NAME = ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".TabMetaFactory";

    private TabMetaFactory() {}

    @ZenCodeType.Method("factory")
    public static CreativeTab factory(final String name) {
        Objects.requireNonNull(name);
        final CreativeModeTab target = Arrays.stream(CreativeModeTab.TABS)
                .filter(it -> name.equals(((CreativeModeTabAccessor) it).contenttweaker$langId()))
                .findFirst()
                .orElseGet(() -> {
                    final CreativeModeTab[] tab = new CreativeModeTab[1];
                    final CreateCreativeTabAction action = CreateCreativeTabAction.of(name, () -> tab[0] = CustomCreativeTab.of(name));
                    CraftTweakerAPI.apply(action);
                    return tab[0];
                });
        return CreativeTab.wrap(target);
    }
}

package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.action.tab.SetCreativeTabIconAction;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.mixin.CreativeModeTabAccessor;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".CreativeTab")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CreativeTab {
    private final CreativeModeTab internal;

    private CreativeTab(final CreativeModeTab tab) {
        this.internal = tab;
    }

    public static CreativeTab wrap(final CreativeModeTab wrapped) {
        return new CreativeTab(Objects.requireNonNull(wrapped));
    }

    @ZenCodeType.Getter("id")
    public String id() {
        return ((CreativeModeTabAccessor) this.internal).contenttweaker$langId();
    }

    @ZenCodeType.Setter("icon")
    public void icon(final ItemReference icon) {
        CraftTweakerAPI.apply(SetCreativeTabIconAction.of(this, icon));
    }

    public CreativeModeTab unwrap() {
        return this.internal;
    }

    @Override
    public String toString() {
        return this.id();
    }
}

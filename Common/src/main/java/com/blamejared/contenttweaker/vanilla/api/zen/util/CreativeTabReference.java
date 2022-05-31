package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.action.tab.SetCreativeTabIconAction;
import com.blamejared.contenttweaker.vanilla.api.util.ContentTweakerCreativeTab;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.base.Suppliers;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".CreativeTabReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class CreativeTabReference {
    private final String id;
    private final Supplier<CreativeModeTab> getter;
    private Function<CreativeModeTab, SetCreativeTabIconAction> applyIconActionCreator;

    private CreativeTabReference(final String id, final Supplier<CreativeModeTab> getter) {
        this.id = id;
        this.getter = Suppliers.memoize(() -> get(getter, () -> this.applyIconActionCreator));
        this.applyIconActionCreator = null;
    }

    public static CreativeTabReference of(final String id, final Supplier<CreativeModeTab> getter) {
        return new CreativeTabReference(Objects.requireNonNull(id), Objects.requireNonNull(getter));
    }

    private static CreativeModeTab get(
            final Supplier<CreativeModeTab> getter,
            final Supplier<Function<CreativeModeTab, SetCreativeTabIconAction>> iconSupplier
    ) {
        final CreativeModeTab tab = getter.get();
        Optional.ofNullable(iconSupplier.get()).map(it -> it.apply(tab)).ifPresent(ContentTweakerApi::apply);
        return tab;
    }

    @ZenCodeType.Getter("id")
    public String id() {
        return this.id;
    }

    @ZenCodeType.Setter("icon")
    public void icon(final ItemReference icon) {
        this.applyIconActionCreator = tab -> SetCreativeTabIconAction.of(this, icon, it -> {
            if (tab instanceof ContentTweakerCreativeTab group) {
                group.setDisplayItem(it);
            } else {
                CraftTweakerAPI.LOGGER.error(() -> "Unable to change icon of non-customizable tab " + this + " to " + icon);
            }
        });
    }

    public CreativeModeTab unwrap() {
        return this.getter.get();
    }

    @Override
    public String toString() {
        return this.id();
    }
}

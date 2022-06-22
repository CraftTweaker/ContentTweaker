package com.blamejared.contenttweaker.vanilla.api.util;

import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.google.common.base.Suppliers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.function.Supplier;

public final class CustomCreativeTab extends CreativeModeTab {
    private final ItemReference displayItem;
    private final Supplier<ItemStack> cache;

    private CustomCreativeTab(final int id, final String langId, final ItemReference displayItem) {
        super(id, langId);
        this.displayItem = Objects.requireNonNull(displayItem);
        this.cache = Suppliers.memoize(this::makeIcon);
    }

    public static CreativeModeTab of(final String id, final ItemReference icon) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(icon);
        while (true) {
            synchronized (CreativeModeTab.TABS) {
                final CreativeModeTab[] tabs = CreativeModeTab.TABS;
                final int numericalId = tabs.length - 1;
                if (tabs[numericalId] == null) {
                    return new CustomCreativeTab(numericalId, id, icon);
                }
            }
        }
    }

    @Override
    public ItemStack getIconItem() {
        return this.cache.get();
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(this.displayItem.get());
    }
}

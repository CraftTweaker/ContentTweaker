package com.blamejared.contenttweaker.vanilla.util;

import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.vanilla.api.resource.Language;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.util.ContentTweakerCreativeTab;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.mixin.CreativeModeTabAccessor;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public final class CustomCreativeTab extends CreativeModeTab implements ContentTweakerCreativeTab {
    private ItemReference displayItem;
    private ItemStack cache;

    private CustomCreativeTab(final int id, final String langId, final ItemReference displayItem) {
        super(id, langId);
        this.displayItem = Objects.requireNonNull(displayItem);
        this.cache = null;
    }

    public static CreativeModeTab of(final String name) {
        Objects.requireNonNull(name);
        final int id = expand();
        final ResourceFragment assets = ResourceManager.get().fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        final CreativeModeTab tab = new CustomCreativeTab(id, name, ItemReference.AIR);
        assets.provideOrAlter(PathHelper.usLang(), Language::of, it -> it.tab(name, "Example Tab"), Language.SERIALIZER);
        return tab;
    }

    private static int expand() {
        final CreativeModeTab[] tabs = CreativeModeTab.TABS;
        final int length = tabs.length;
        final CreativeModeTab[] newTabs = new CreativeModeTab[length + 1];
        System.arraycopy(tabs, 0, newTabs, 0, length);
        newTabs[length] = null;
        CreativeModeTabAccessor.contenttweaker$tabs(newTabs);
        return length;
    }

    @Override
    public void setDisplayItem(final ItemReference stack) {
        this.displayItem = Objects.requireNonNull(stack);
        this.cache = null;
    }

    @Override
    public ItemStack getIconItem() {
        if (this.cache == null) {
            this.cache = this.makeIcon();
        }
        return this.cache;
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(this.displayItem.get());
    }
}

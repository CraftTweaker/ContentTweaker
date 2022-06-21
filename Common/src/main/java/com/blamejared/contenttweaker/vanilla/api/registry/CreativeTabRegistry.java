package com.blamejared.contenttweaker.vanilla.api.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.mixin.CreativeModeTabAccessor;
import com.google.common.base.CaseFormat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class CreativeTabRegistry implements GameRegistry<CreativeModeTab> {
    private final Map<ResourceLocation, CreativeModeTab> fastLookup;
    private final Map<String, Supplier<CreativeModeTab>> creators;

    private CreativeTabRegistry() {
        this.fastLookup = new HashMap<>();
        this.creators = new HashMap<>();
    }

    public static CreativeTabRegistry of() {
        return new CreativeTabRegistry();
    }

    public static ResourceLocation fromId(final String name) {
        return new ResourceLocation(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, Objects.requireNonNull(name)));
    }

    public static String toId(final ResourceLocation name) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, Objects.requireNonNull(name).getPath());
    }

    @Override
    public ObjectType<CreativeModeTab> type() {
        return VanillaObjectTypes.CREATIVE_TAB;
    }

    @Override
    public CreativeModeTab get(final ResourceLocation name) {
        if (this.fastLookup.containsKey(name)) {
            return this.fastLookup.get(name);
        }

        final String id = toId(name);
        if (this.creators.containsKey(id)) {
            this.register(this.creators.get(id));
            this.creators.remove(id);
        }

        final CreativeModeTab tab = Arrays.stream(CreativeModeTab.TABS)
                .filter(it -> id.equals(((CreativeModeTabAccessor) it).contenttweaker$langId()))
                .findFirst()
                .orElse(null);
        this.fastLookup.put(name, tab);
        return tab;
    }

    @Override
    public ResourceLocation nameOf(final CreativeModeTab object) {
        return fromId(((CreativeModeTabAccessor) Objects.requireNonNull(object)).contenttweaker$langId());
    }

    @Override
    public Collection<CreativeModeTab> all() {
        return Arrays.asList(CreativeModeTab.TABS);
    }

    @Override
    public void enqueueRegistration(final ResourceLocation name, final Supplier<CreativeModeTab> objectCreator) {
        final String id = toId(name);
        if (this.creators.containsKey(id)) {
            throw new IllegalStateException("Already registered a creative tab for id '" + id + '\'');
        }
        this.creators.put(id, objectCreator);
    }

    private void register(final Supplier<CreativeModeTab> tab) {
        this.expandByOne();
        tab.get(); // Creating the tab automatically registers it because that's how vanilla behaves
    }

    private void expandByOne() {
        final CreativeModeTab[] tabs = CreativeModeTab.TABS;
        final int length = tabs.length;
        final CreativeModeTab[] newTabs = new CreativeModeTab[length + 1];
        System.arraycopy(tabs, 0, newTabs, 0, length);
        newTabs[length] = null;
        CreativeModeTabAccessor.contenttweaker$tabs(newTabs);
    }
}

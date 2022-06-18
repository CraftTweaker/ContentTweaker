package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Material;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".MaterialReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MaterialReference {
    private final ResourceLocation name;
    private final Supplier<Material> lookupManager;

    private MaterialReference(final ResourceLocation name, final Supplier<Material> lookupManager) {
        this.name = name;
        this.lookupManager = lookupManager;
    }

    public static MaterialReference of(final ResourceLocation name, final Supplier<Material> lookupManager) {
        return new MaterialReference(Objects.requireNonNull(name), Suppliers.memoize(Objects.requireNonNull(lookupManager)::get));
    }

    public static MaterialReference of(final ResourceLocation name, final Function<ResourceLocation, Material> lookupFunction) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(lookupFunction);
        return of(name, () -> lookupFunction.apply(name));
    }

    @ZenCodeType.Method("builder")
    public static MaterialBuilder builder(final MaterialColorReference reference) {
        return MaterialBuilder.of(reference);
    }

    @ZenCodeType.Getter("name")
    public ResourceLocation name() {
        return this.name;
    }

    public Material unwrap() {
        return this.lookupManager.get();
    }
}

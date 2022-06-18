package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".SoundTypeReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundTypeReference {
    private final ResourceLocation name;
    private final Supplier<SoundType> supplier;

    private SoundTypeReference(final ResourceLocation name, final Supplier<SoundType> supplier) {
        this.name = name;
        this.supplier = supplier;
    }

    public static SoundTypeReference of(final ResourceLocation name, final Supplier<SoundType> supplier) {
        return new SoundTypeReference(Objects.requireNonNull(name), Suppliers.memoize(Objects.requireNonNull(supplier)::get));
    }

    @ZenCodeType.Method("builder")
    public static SoundTypeBuilder builder() {
        return SoundTypeBuilder.of();
    }

    @ZenCodeType.Getter("name")
    public ResourceLocation name() {
        return this.name;
    }

    public SoundType unwrap() {
        return this.supplier.get();
    }
}

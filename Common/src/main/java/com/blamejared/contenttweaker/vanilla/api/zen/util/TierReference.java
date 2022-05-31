package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".TierReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierReference {
    private final ResourceLocation name;
    private final int level;
    private final Supplier<Tier> tierSupplier;

    private TierReference(final ResourceLocation name, final int level, final Function<TierReference, Tier> tierSupplier) {
        this.name = name;
        this.level = level;
        this.tierSupplier = Suppliers.memoize(() -> tierSupplier.apply(this));
    }

    public static TierReference of(final ResourceLocation name, final int level, final Function<TierReference, Tier> tierSupplier) {
        return new TierReference(Objects.requireNonNull(name), level, Objects.requireNonNull(tierSupplier));
    }

    public static TierReference of(final ResourceLocation name, final int level, final Supplier<Tier> tierSupplier) {
        Objects.requireNonNull(tierSupplier);
        return of(name, level, ignored -> tierSupplier.get());
    }

    @ZenCodeType.Getter("name")
    public ResourceLocation name() {
        return this.name;
    }

    @ZenCodeType.Getter("level")
    public int level() {
        return this.level;
    }

    public Tier unwrap() {
        return this.tierSupplier.get();
    }
}

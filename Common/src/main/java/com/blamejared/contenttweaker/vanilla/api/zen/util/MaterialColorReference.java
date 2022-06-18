package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.util.Color;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.base.Suppliers;
import net.minecraft.world.level.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".MaterialColorReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MaterialColorReference {
    private final int id;
    private final Color color;
    private final Supplier<MaterialColor> supplier;

    private MaterialColorReference(final int id, final Color color, final Supplier<MaterialColor> colorSupplier) {
        this.id = id;
        this.color = color;
        this.supplier = Suppliers.memoize(colorSupplier::get);
    }

    public static MaterialColorReference of(final int id, final Color color, final IntFunction<MaterialColor> colorSupplier) {
        if (id < 0 || id > 64) {
            throw new IllegalStateException("Invalid bounds for ID " + id);
        }
        Objects.requireNonNull(colorSupplier);
        return new MaterialColorReference(id, color, () -> colorSupplier.apply(id));
    }

    @ZenCodeType.Getter("id")
    public int id() {
        return this.id;
    }

    @ZenCodeType.Getter("color")
    public Color color() {
        return this.color;
    }

    public MaterialColor unwrap() {
        return this.supplier.get();
    }
}

package com.blamejared.contenttweaker.vanilla.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.api.zen.util.Color;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".MaterialColorReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MaterialColorReference extends Reference<MaterialColor> {
    private final Supplier<Integer> numericalId;
    private final Supplier<Color> color;

    private MaterialColorReference(final ResourceLocation id) {
        super(VanillaObjectTypes.MATERIAL_COLOR, id);
        this.numericalId = Suppliers.memoize(this::lookupId);
        this.color = Suppliers.memoize(this::lookupColor);
    }

    @ZenCodeType.Method("of")
    public static MaterialColorReference of(final ResourceLocation id) {
        return new MaterialColorReference(Objects.requireNonNull(id));
    }

    @ZenCodeType.Getter("numericalId")
    public int numericalId() {
        return this.numericalId.get();
    }

    @ZenCodeType.Getter("color")
    public Color color() {
        return this.color.get();
    }

    private int lookupId() {
        // TODO("")
        return 0;
    }

    private Color lookupColor() {
        // TODO("")
        return Color.packedRgb(0);
    }
}

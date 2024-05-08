package com.blamejared.contenttweaker.vanilla.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.api.zen.util.Color;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MapColor;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Supplier;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".MapColorReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MapColorReference extends Reference<MapColor> {
    private final Supplier<Color> color;

    private MapColorReference(final ResourceLocation id) {
        super(VanillaObjectTypes.MAP_COLOR, id);
        this.color = Suppliers.memoize(this::lookupColor);
    }

    @ZenCodeType.Method("of")
    public static MapColorReference of(final ResourceLocation id) {
        return new MapColorReference(Objects.requireNonNull(id));
    }

    @ZenCodeType.Getter("color")
    public Color color() {
        return this.color.get();
    }

    private Color lookupColor() {
        return Color.packedRgb(this.get().col);
    }
}

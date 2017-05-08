package com.teamacronymcoders.contenttweaker.modules.materials.materialparts;

import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.MaterialPart")
public interface IMaterialPart {
    @ZenMethod
    String getName();

    @ZenMethod
    String getLocalizedName();

    @ZenMethod
    boolean hasEffect();

    @ZenMethod
    IMaterial getMaterial();

    @ZenMethod
    IPart getPart();

    @ZenMethod
    IItemStack getItemStack();

    @ZenMethod
    String getTextureLocation();

    @ZenMethod
    void setTextureLocation(String textureLocation);

    @ZenMethod
    int getColor();

    @ZenMethod
    boolean isColorized();

    @ZenMethod
    void setColorized(boolean colorized);

    @ZenMethod
    IMaterialPartData getData();
}

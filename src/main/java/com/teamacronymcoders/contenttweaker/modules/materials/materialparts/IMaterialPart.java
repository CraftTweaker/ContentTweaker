package com.teamacronymcoders.contenttweaker.modules.materials.materialparts;

import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.contenttweaker.MaterialPart")
public interface IMaterialPart {
    @ZenGetter("name")
    String getName();

    @ZenGetter("name")
    String getLocalizedName();

    @ZenGetter("name")
    boolean hasEffect();

    @ZenGetter("material")
    IMaterial getMaterial();

    @ZenGetter("part")
    IPart getPart();

    @ZenGetter("itemStack")
    IItemStack getItemStack();

    @ZenGetter("textureLocation")
    String getTextureLocation();

    @ZenSetter("textureLocation")
    void setTextureLocation(String textureLocation);

    @ZenGetter("color")
    int getColor();

    @ZenGetter("colorize")
    boolean isColorize();

    @ZenSetter("colorize")
    void setColorize(boolean colorize);

    @ZenGetter("data")
    IMaterialPartData getData();
}

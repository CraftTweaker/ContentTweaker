package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.IMaterialDefinition;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.Block")
public interface IBlock {
    @ZenMethod
    String getUnlocalizedName();

    @ZenMethod
    void setUnlocalizedName(String unlocalizedName);

    @ZenMethod
    void setCreativeTab(ICreativeTab creativeTab);

    @ZenMethod
    ICreativeTab getCreativeTab();

    @ZenMethod
    boolean isFullBlock();

    @ZenMethod
    void setFullBlock(boolean fullBlock);

    @ZenMethod
    int getLightOpacity();

    @ZenMethod
    void setLightOpacity(int lightOpacity);

    @ZenMethod
    boolean isTranslucent();

    @ZenMethod
    void setTranslucent(boolean translucent);

    @ZenMethod
    int getLightValue();

    @ZenMethod
    void setLightValue(int lightValue);

    @ZenMethod
    float getBlockHardness();

    @ZenMethod
    void setBlockHardness(float blockHardness);

    @ZenMethod
    float getBlockResistance();

    @ZenMethod
    void setBlockResistance(float blockResistance);

    @ZenMethod
    String getToolClass();

    @ZenMethod
    void setToolClass(String toolClass);

    @ZenMethod
    int getToolLevel();

    @ZenMethod
    void setToolLevel(int toolLevel);

    @ZenMethod
    void setBlockSoundType(String blockSoundTypeName);

    @ZenMethod
    void setBlockMaterial(String blockMaterialName);

    @ZenMethod
    void setBlockMaterial(IMaterialDefinition material);

    @ZenMethod
    void register();

    Object getInternal();
}

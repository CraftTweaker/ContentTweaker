package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.IMaterialDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.ISoundTypeDefinition;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.contenttweaker.Block")
public interface IBlock {
    @ZenGetter("unlocalizedName")
    String getUnlocalizedName();

    @ZenSetter("unlocalizedName")
    void setUnlocalizedName(String unlocalizedName);

    @ZenGetter("creativeTab")
    void setCreativeTab(ICreativeTab creativeTab);

    @ZenSetter("creativeTab")
    ICreativeTab getCreativeTab();

    @ZenGetter("fullBlock")
    boolean isFullBlock();

    @ZenSetter("fullBlock")
    void setFullBlock(boolean fullBlock);

    @ZenGetter("lightOpacity")
    int getLightOpacity();

    @ZenSetter("lightOpacity")
    void setLightOpacity(int lightOpacity);

    @ZenGetter("translucent")
    boolean isTranslucent();

    @ZenSetter("translucent")
    void setTranslucent(boolean translucent);

    @ZenGetter("lightValue")
    float getLightValue();

    @ZenSetter("lightValue")
    void setLightValue(float lightValue);

    @ZenGetter("blockHardness")
    float getBlockHardness();

    @ZenSetter("blockHardness")
    void setBlockHardness(float blockHardness);

    @ZenGetter("blockResistance")
    float getBlockResistance();

    @ZenSetter("blockResistance")
    void setBlockResistance(float blockResistance);

    @ZenGetter("toolClass")
    String getToolClass();

    @ZenSetter("toolClass")
    void setToolClass(String toolClass);

    @ZenGetter("toolLevel")
    int getToolLevel();

    @ZenSetter("toolLevel")
    void setToolLevel(int toolLevel);

    @ZenGetter("blockSoundType")
    ISoundTypeDefinition getBlockSoundType();

    @ZenSetter("blockSoundType")
    void setBlockSoundType(ISoundTypeDefinition blockSoundTypeName);

    @ZenGetter("blockMaterial")
    IMaterialDefinition getBlockMaterial();

    @ZenSetter("blockMaterial")
    void setBlockMaterial(IMaterialDefinition material);

    @ZenSetter("enchantPowerBonus")
    void setEnchantPowerBonus(float enchantPowerBonus);

    @ZenGetter("enchantPowerBonus")
    float getEnchantPowerBonus();
    
    @ZenMethod
    void register();

    Object getInternal();
}

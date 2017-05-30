package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockAction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.IMaterialDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.ISoundTypeDefinition;
import net.minecraft.util.BlockRenderLayer;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.contenttweaker.Block")
public interface IBlock extends IRepresentation {
    @ZenGetter("unlocalizedName")
    String getUnlocalizedName();

    @ZenSetter("unlocalizedName")
    void setUnlocalizedName(String unlocalizedName);

    @ZenGetter("creativeTab")
    ICreativeTab getCreativeTab();

    @ZenSetter("creativeTab")
    void setCreativeTab(ICreativeTab creativeTab);

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

    @ZenSetter("blockSoundType")
    void setBlockSoundType(ISoundTypeDefinition blockSoundTypeName);

    @ZenGetter("blockSoundType")
    ISoundTypeDefinition getBlockSoundType();

    @ZenSetter("blockMaterial")
    void setBlockMaterial(IMaterialDefinition material);

    @ZenGetter("blockMaterial")
    IMaterialDefinition getBlockMaterial();

    @ZenSetter("enchantPowerBonus")
    void setEnchantPowerBonus(float enchantPowerBonus);

    @ZenGetter("enchantPowerBonus")
    float getEnchantPowerBonus();

    @ZenSetter("enumBlockRenderType")
    void setEnumBlockRenderType(String blockRenderType);

    @ZenGetter("enumBlockRenderType")
    String getEnumBlockRenderType();

    @ZenSetter("slipperiness")
    void setSlipperiness(float slipperiness);

    @ZenGetter("slipperiness")
    float getSlipperiness();

    @ZenSetter("onBlockBreak")
    void setOnBlockBreak(IBlockAction iBlockAction);

    @ZenGetter("onBlockBreak")
    IBlockAction getOnBlockBreak();

    @ZenSetter("onBlockPlace")
    void setOnBlockPlace(IBlockAction iBlockAction);

    @ZenGetter("onBlockPlace")
    IBlockAction getOnBlockAdded();

    @ZenSetter("blockLayer")
    void setBlockLayer(String blockLayer);

    @ZenGetter("blockLayer")
    String getBlockLayer();

    @ZenMethod
    void register();
}

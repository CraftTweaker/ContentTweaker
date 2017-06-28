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
    @ZenMethod
    String getUnlocalizedName();

    @ZenMethod
    void setUnlocalizedName(String unlocalizedName);

    @ZenMethod
    ICreativeTab getCreativeTab();

    @ZenMethod
    void setCreativeTab(ICreativeTab creativeTab);

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
    float getLightValue();

    @ZenMethod
    void setLightValue(float lightValue);

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
    void setBlockSoundType(ISoundTypeDefinition blockSoundTypeName);

    @ZenMethod
    ISoundTypeDefinition getBlockSoundType();

    @ZenMethod
    void setBlockMaterial(IMaterialDefinition material);

    @ZenMethod
    IMaterialDefinition getBlockMaterial();

    @ZenMethod
    void setEnchantPowerBonus(float enchantPowerBonus);

    @ZenMethod
    float getEnchantPowerBonus();

    @ZenMethod
    void setEnumBlockRenderType(String blockRenderType);

    @ZenMethod
    String getEnumBlockRenderType();

    @ZenMethod
    void setSlipperiness(float slipperiness);

    @ZenMethod
    float getSlipperiness();

    @ZenMethod
    void setOnBlockBreak(IBlockAction iBlockAction);

    @ZenMethod
    IBlockAction getOnBlockBreak();

    @ZenMethod
    void setOnBlockPlace(IBlockAction iBlockAction);

    @ZenMethod
    IBlockAction getOnBlockAdded();

    @ZenMethod
    void setBlockLayer(String blockLayer);

    @ZenMethod
    String getBlockLayer();

    @ZenMethod
    void register();
}

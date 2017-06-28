package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemRightClick;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.contenttweaker.Item")
public interface IItem extends IRepresentation {
    @ZenMethod
    String getUnlocalizedName();

    @ZenMethod
    void setUnlocalizedName(String unlocalizedName);

    @ZenMethod
    int getMaxStackSize();

    @ZenMethod
    void setMaxStackSize(int maxStackSize);

    @ZenMethod
    String getRarity();

    @ZenMethod
    void setRarity(String rarity);

    @ZenMethod
    ICreativeTab getCreativeTab();

    @ZenMethod
    void setCreativeTab(ICreativeTab creativeTab);

    @ZenMethod
    float getSmeltingExperience();

    @ZenMethod
    void setSmeltingExperience(float smeltingExperience);

    @ZenMethod
    String getToolClass();

    @ZenMethod
    void setToolClass(String toolClass);

    @ZenMethod
    int getToolLevel();

    @ZenMethod
    void setToolLevel(int toolLevel);

    @ZenMethod
    boolean isBeaconPayment();

    @ZenMethod
    void setBeaconPayment(boolean beaconPayment);

    @ZenMethod
    IItemRightClick getItemRightClick();

    @ZenMethod
    void setItemRightClick(IItemRightClick itemRightClick);

    @ZenMethod
    String getItemUseAction();

    @ZenMethod
    void setItemUseAction(String itemUseAction);

    @ZenMethod
    void register();
}

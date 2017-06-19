package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemRightClick;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.contenttweaker.Item")
public interface IItem extends IRepresentation {
    @ZenGetter("unlocalizedName")
    String getUnlocalizedName();

    @ZenSetter("unlocalizedName")
    void setUnlocalizedName(String unlocalizedName);

    @ZenGetter("maxStackSize")
    int getMaxStackSize();

    @ZenSetter("maxStackSize")
    void setMaxStackSize(int maxStackSize);

    @ZenGetter("rarity")
    String getRarity();

    @ZenSetter("rarity")
    void setRarity(String rarity);

    @ZenGetter("creativeTab")
    ICreativeTab getCreativeTab();

    @ZenSetter("creativeTab")
    void setCreativeTab(ICreativeTab creativeTab);

    @ZenGetter("smeltingExperience")
    float getSmeltingExperience();

    @ZenSetter("smeltingExperience")
    void setSmeltingExperience(float smeltingExperience);

    @ZenGetter("toolClass")
    String getToolClass();

    @ZenSetter("toolClass")
    void setToolClass(String toolClass);

    @ZenGetter("toolLevel")
    int getToolLevel();

    @ZenSetter("toolLevel")
    void setToolLevel(int toolLevel);

    @ZenGetter("beaconPayment")
    boolean isBeaconPayment();

    @ZenSetter("beaconPayment")
    void setBeaconPayment(boolean beaconPayment);

    @ZenGetter("itemRightClick")
    IItemRightClick getItemRightClick();

    @ZenSetter("itemRightClick")
    void setItemRightClick(IItemRightClick itemRightClick);

    @ZenGetter("itemUseAction")
    String getItemUseAction();

    @ZenSetter("itemUseAction")
    void setItemUseAction(String itemUseAction);

    @ZenMethod
    void register();
}
